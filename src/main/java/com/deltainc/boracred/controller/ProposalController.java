package com.deltainc.boracred.controller;

import com.deltainc.boracred.configuration.AWSConfig;
import com.deltainc.boracred.dto.ProposalRegisterDTO;
import com.deltainc.boracred.dto.ProposalUpdateDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import com.deltainc.boracred.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/api/v1/proposal")
public class ProposalController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AnalyticsRepository analyticsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    FilesRepository filesRepository;

    @PostMapping("/register")
    public ResponseEntity registerProposal(@RequestBody ProposalRegisterDTO registerData){
        try {
            Proposal proposal = new Proposal();
            Optional<Users> optionalUser = usersRepository.findById(registerData.getUser_id());
            Users user = optionalUser.get();
            Optional<Customer> optionalCustomer = customerRepository.findById(registerData.getCustomer_id());
            Customer customer = optionalCustomer.get();
            proposal.setCustomer(customer);
            proposal.setValor_desejado(registerData.getValor_desejado());
            proposal.setPrazo(registerData.getPrazo());
            proposal.setStatus("Em análise");
            proposal.setObservacao_cliente(registerData.getObservacao_cliente());
            proposal.setData_abertura(LocalDate.now());
            proposal.setUser(user);
            proposalRepository.save(proposal);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }catch(Exception error){
            Map<String, Object> response = new HashMap<>();
            response.put("erro", error.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbyuser/{id}")
    public ResponseEntity getByUser(@PathVariable Integer id){
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer != null) {
            List<HashMap<String, Object>> listResponse = new ArrayList<>();
            Customer customer = optionalCustomer.get();
            List<Proposal> proposals = proposalRepository.findByCustomer(customer);
            for (Proposal proposal : proposals) {
                HashMap<String, Object> response = new HashMap<>();
                Users user = proposal.getUser();
                System.out.println(user);
                response.put("tipo", customer.is_cnpj());
                response.put("username", user.getUsername());
                response.put("business", customer.getBusiness());
                response.put("date", proposal.getData_abertura());
                response.put("razaoSocial", customer.getRazao_social());
                response.put("nomeCompleto", customer.getNome_completo());
                response.put("cpf", customer.getCpf());
                response.put("cnpj", customer.getCnpj());
                response.put("status", proposal.getStatus());
                listResponse.add(response);
            }
            return new ResponseEntity<>(listResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.OK);
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
            proposalRepository.save(proposal);
            Analytics analytics = new Analytics();

            analytics.setProposal(proposal);

            if (analytics.getNum_titulos_protestados() != data.getNum_titulos_protestados()){
                analytics.setNum_titulos_protestados(data.getNum_titulos_protestados());
            }
            if (analytics.getScore() != data.getScore()){
                analytics.setScore(data.getScore());
            }
            if (analytics.getNum_refins() != data.getNum_refins()){
                analytics.setNum_refins((data.getNum_refins()));
            }
            if (analytics.getValor_cadins() != data.getValor_cadins()){
                analytics.setValor_cadins(data.getValor_cadins());
            }
            if (analytics.getValor_iss() != data.getValor_iss()){
                analytics.setValor_iss(data.getValor_iss());
            }
            if (analytics.getNum_processos() != data.getNum_processos()){
                analytics.setNum_processos(data.getNum_processos());
            }
            if (analytics.getValor_processos() != data.getValor_processos()){
                analytics.setValor_processos(data.getValor_processos());
            }
            if (analytics.getNum_uf_processos() != data.getNum_uf_processos()){
                analytics.setNum_uf_processos(data.getNum_uf_processos());
            }
            if (analytics.getDivida_ativa() != data.getDivida_ativa()){
                analytics.setDivida_ativa(data.getDivida_ativa());
            }
            if (analytics.getValor_titulos_protestados() != data.getValor_titulos_protestados()){
                analytics.setValor_titulos_protestados(data.getValor_titulos_protestados());
            }
            if (analytics.getRisco() != data.getRisco()){
                analytics.setRisco(data.getRisco());
            }
            analyticsRepository.save(analytics);
        }
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

}
