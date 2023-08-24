package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.ProposalRegisterDTO;
import com.deltainc.boracred.dto.ProposalUpdateDTO;
import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.repositories.CustomerRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/proposal")
public class ProposalController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity registerProposal(@RequestBody ProposalRegisterDTO registerData){
        try {
            Proposal proposal = new Proposal();
            Optional<Customer> optionalCustomer = customerRepository.findById(registerData.getCustomer_id());
            Customer customer = optionalCustomer.get();
            proposal.setCustomer_id(customer);
            proposal.setValor_desejado(registerData.getValor_desejado());
            proposal.setPrazo(registerData.getPrazo());
            proposal.setStatus("Em análise");
            proposal.setObservacao_cliente(registerData.getObservacao_cliente());
            proposal.setData_abertura(LocalDate.now());
            proposalRepository.save(proposal);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }catch(Exception error){
            Map<String, Object> response = new HashMap<>();
            response.put("erro", error.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity getall(){
        return new ResponseEntity<>(proposalRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getbyid")
    public ResponseEntity getbyid(@PathVariable Integer id){
        return new ResponseEntity<>(proposalRepository.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody ProposalUpdateDTO data){
        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
        if (optionalProposal.isPresent()){
            Proposal proposal = optionalProposal.get();
            if (proposal.getValor_desejado() != data.getValor_desejado()){
                proposal.setValor_desejado(data.getValor_desejado());
            }
            if (proposal.getTaxa() != data.getTaxa()){
                proposal.setTaxa(data.getTaxa());
            }
            if (proposal.getCorban() != data.getCorban()){
                proposal.setCorban(data.getCorban());
            }
            if (!proposal.getStatus().equals(data.getStatus())){
                proposal.setStatus(data.getStatus());
            }
            if (proposal.getMontante() != data.getMontante()){
                proposal.setMontante(data.getMontante());
            }
            if (proposal.getValor_liberado() != data.getMontante()){
                proposal.setValor_liberado(data.getValor_liberado());
            }
            if (proposal.getPrazo() != data.getPrazo()){
                proposal.setPrazo(data.getPrazo());
            }
            if (!proposal.getData_abertura().equals(data.getData_abertura())){
                proposal.setData_abertura(data.getData_abertura());
            }
            if (!proposal.getData_primeira_parcela().equals(data.getData_primeira_parcela())){
                proposal.setData_primeira_parcela(data.getData_primeira_parcela());
            }
            if (proposal.getTotal_juros() != data.getTotal_juros()){
                proposal.setTotal_juros(data.getTotal_juros());
            }
            if (!proposal.getStatus_contrato().equals(data.getStatus_contrato())){
                proposal.setStatus_contrato(data.getStatus_contrato());
            }
            if (!proposal.getMotivo_reprovacao().equals(data.getMotivo_reprovacao())){
                proposal.setMotivo_reprovacao(data.getMotivo_reprovacao());
            }
            if (!proposal.getObservacao_cliente().equals(data.getObservacao_cliente())){
                proposal.setObservacao_cliente(data.getObservacao_cliente());
            }
            if (!proposal.getObservacao_analista().equals(data.getObservacao_analista())){
                proposal.setObservacao_analista(data.getObservacao_analista());
            }
        }
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

}
