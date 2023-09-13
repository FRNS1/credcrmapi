package com.deltainc.boracred.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/v1/payments")
public class FluxoDePagamentosController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    FluxoDePagamentosRepository fluxoDePagamentosRepository;


    @GetMapping("/getloans")
    public ResponseEntity getLoans(){
        try {
            List<Proposal> loans = proposalRepository.getAllLoans();
            List<HashMap<String, Object>> listLoans = new ArrayList<>();
            for (Proposal proposal : loans){
                Map<String, Object> response = new HashMap<>();
                Customer customer = proposal.getCustomer();
                response.put("business", customer.getBusiness());
                response.put("idCliente", customer.getCustomer_id());
                response.put("nomeCliente", customer.getNome_completo());
                response.put("razaoSocial", customer.getRazao_social());
                float saldoDevedor;
                List<FluxoDePagamentos> payments = fluxoDePagamentosRepository.findAllByProposal(proposal.getProposal_id());
                for (FluxoDePagamentos payment : payments){

                }
            }
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
