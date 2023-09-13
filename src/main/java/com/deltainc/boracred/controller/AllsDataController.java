package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.dto.AllsDataRegisterDTO;
import com.deltainc.boracred.entity.AllsData;
import com.deltainc.boracred.repositories.AllsDataRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.deltainc.boracred.entity.Proposal;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1/allsdata")
public class AllsDataController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    AllsDataRepository allsDataRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AllsDataRegisterDTO data){
        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
        Proposal proposal = optionalProposal.get();
        AllsData allsData = new AllsData();
        allsData.setNum_pendencias_financeiras(data.getNum_pendencias_financeiras());
        allsData.setValor_pendencias_financeiras(data.getValor_pendencias_financeiras());
        allsData.setNum_recuperacoes(data.getNum_recuperacoes());
        allsData.setValor_recuperacoes(data.getValor_recuperacoes());
        allsData.setNum_cheque_sem_fundo(data.getNum_cheque_sem_fundo());
        allsData.setNum_protestos(data.getNum_protestos());
        allsData.setValor_protestos(data.getValor_protestos());
        allsData.setLimite_sugerido(data.getLimite_sugerido());
        allsData.setNum_restricoes(data.getNum_restricoes());
        allsData.setValor_restricoes(data.getValor_restricoes());
        allsData.setProposal(proposal);
        allsDataRepository.save(allsData);
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

}
