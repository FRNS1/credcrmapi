package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.FluxoDePagamentos;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.repositories.CustomerRepository;
import com.deltainc.boracred.repositories.FluxoDePagamentosRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("api/v1/payments")
public class FluxoDePagamentosController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    FluxoDePagamentosRepository fluxoDePagamentosRepository;

    @GetMapping("/closeloan/{id}")
    public ResponseEntity closeLoan(@PathVariable Integer id){
        try{
        List<FluxoDePagamentos> fluxoDePagamentos = fluxoDePagamentosRepository.findAllByProposal(id);
        Optional<Proposal> optionalProposal = proposalRepository.findById(id);
        if (optionalProposal.isPresent()){
            Proposal proposal = optionalProposal.get();
            proposal.setStatus_contrato("QUITADO");
            proposalRepository.save(proposal);
        } else {
            return new ResponseEntity<>("Proposal not found", HttpStatus.OK);
        }
        for (FluxoDePagamentos payment : fluxoDePagamentos){
            payment.setPago("PAGO");
            fluxoDePagamentosRepository.save(payment);
        }
        return new ResponseEntity<>("Contrato quitado", HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
    }

    @GetMapping("/getloandetails/{id}")
    public ResponseEntity getLoanDetails(@PathVariable Integer id){
        try{
            List<FluxoDePagamentos> fluxoDePagamentos = fluxoDePagamentosRepository.findAllByProposal(id);
            return new ResponseEntity<>(fluxoDePagamentos, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getloans")
    public ResponseEntity getLoans(){
        try {
            System.out.println("Getting list of contracts");
            List<Proposal> loans = proposalRepository.getAllLoans();
            System.out.println(loans);
            System.out.println("Got list of contracts");
            List<Map<String, Object>> listLoans = new ArrayList<>();
            for (Proposal proposal : loans){
                Map<String, Object> response = new HashMap<>();
                Customer customer = proposal.getCustomer();
                System.out.println(customer);
                float saldoDevedor = 0;
                float receitaEsperada = 0;
                float amortizacaoPaga = 0;
                float jurosPagos = 0;
                int parcelasPagas = 0;
                int parcelasAtrasadas = 0;
                boolean atrasado = false;
                float totalAtrasado = 0;
                System.out.println("Getting lines for proposal: " + proposal.getProposalId());
                List<FluxoDePagamentos> payments = fluxoDePagamentosRepository.findAllByProposal(proposal.getProposalId());
                System.out.println("Got lines for proposal: " + proposal.getProposalId());
                for (FluxoDePagamentos payment : payments){
                    System.out.println("Verificando pagamento da parcela: " + payment.getParcela_id());
                    if ("VIGENTE".equals(payment.getPago())){
                        System.out.println("Parcela " + payment.getParcela_id() + " Vigente");
                        saldoDevedor = payment.getSaldo_devedor();
                        break;
                    } else if ("EM ATRASO".equals(payment.getPago())){
                        System.out.println("Parcela " + payment.getParcela_id() + " em atraso");
                        saldoDevedor = payment.getSaldo_devedor();
                        break;
                    }
                }
                for (FluxoDePagamentos payment2 : payments){
                    System.out.println("Calculando receita esperada");
                    receitaEsperada = receitaEsperada + payment2.getJuros();
                    if ("PAGO".equals(payment2.getPago())){
                        System.out.println("Parcela paga");
                        amortizacaoPaga = amortizacaoPaga + payment2.getAmortizacao();
                        jurosPagos = jurosPagos + payment2.getJuros();
                        parcelasPagas = parcelasPagas + 1;
                    } else if ("EM ATRASO".equals(payment2.getPago())){
                        System.out.println("Parcela em atraso");
                        parcelasAtrasadas = parcelasAtrasadas + 1;
                        atrasado = true;
                        totalAtrasado = totalAtrasado + payment2.getPagamento();
                        String totalAtrasadoFormatado = String.format("%.2f", totalAtrasado); // "%.2f" formata para duas casas decimais
                    }
                }
                System.out.println(payments);
                response.put("proposalId", proposal.getProposalId());
                response.put("isCnpj", customer.is_cnpj());
                response.put("business", customer.getBusiness());
                response.put("idCliente", customer.getCustomer_id());
                response.put("nomeCliente", customer.getNome_completo());
                response.put("razaoSocial", customer.getRazao_social());
                response.put("saldoDevedor", saldoDevedor);
                response.put("receitaEsperada", receitaEsperada);
                response.put("parcelas", proposal.getPrazo());
                response.put("amortizacaoPaga", amortizacaoPaga);
                response.put("jurosPagos", jurosPagos);
                response.put("parcelasPagas", parcelasPagas);
                response.put("parcelasAtrasadas", parcelasAtrasadas);
                response.put("atrasado", atrasado);
                response.put("totalAtrasado", totalAtrasado);
                response.put("statusContrato", proposal.getStatus_contrato());
                listLoans.add(response);
            }
            return new ResponseEntity<>(listLoans, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
