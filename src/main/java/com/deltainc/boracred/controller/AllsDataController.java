package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.dto.AllsDataRegisterDTO;
import com.deltainc.boracred.dto.AllsDataUpdateDTO;
import com.deltainc.boracred.entity.AllsData;
import com.deltainc.boracred.entity.Logs;
import com.deltainc.boracred.entity.Users;
import com.deltainc.boracred.repositories.AllsDataRepository;
import com.deltainc.boracred.repositories.LogsRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import com.deltainc.boracred.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.deltainc.boracred.entity.Proposal;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/allsdata")
public class AllsDataController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AllsDataRepository allsDataRepository;

    @Autowired
    LogsRepository logsRepository;

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

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody AllsDataUpdateDTO data) {
        try {
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
            Optional<Users> optionalUsers = usersRepository.findById(data.getUser_id());
            Users users = optionalUsers.get();
            if (optionalProposal.isPresent()) {
                Proposal proposal = optionalProposal.get();
                AllsData allsData = allsDataRepository.findByProposal(proposal);
                if (allsData != null) {
                    // Logs
                    String action = "Update";
                    LocalDateTime dataAcao = LocalDateTime.now();
                    String newValue = "num_pendencias_financeirasAlls = " + data.getNum_pendencias_financeiras() + ", valor_pendencias_financeirasAlls = " + data.getValor_pendencias_financeiras() + ", num_recuperacoes = " + data.getNum_recuperacoes() + ", valor_recuperacoes = " + data.getValor_recuperacoes() + ", num_cheque_sem_fundo = " + data.getNum_cheque_sem_fundo() +
                            ", num_protestos = " + data.getNum_protestos() + ", valor_protestos = " + data.getValor_protestos() + ", limite_sugerido = " + data.getLimite_sugerido() + ", num_restricoes = " + data.getNum_restricoes() + ", valor_restricoes = " + data.getValor_restricoes();
                    String oldValue = "num_pendencias_financeirasAlls = " + allsData.getNum_pendencias_financeiras() + ", valor_pendencias_financeirasAlls = " + allsData.getValor_pendencias_financeiras() + ", num_recuperacoes = " + allsData.getNum_recuperacoes() + ", valor_recuperacoes = " + allsData.getValor_recuperacoes() + ", num_cheque_sem_fundo = " + allsData.getNum_cheque_sem_fundo() +
                            ", num_protestos = " + allsData.getNum_protestos() + ", valor_protestos = " + allsData.getValor_protestos() + ", limite_sugerido = " + allsData.getLimite_sugerido() + ", num_restricoes = " + allsData.getNum_restricoes() + ", valor_restricoes = " + allsData.getValor_restricoes();
                    Integer target = allsData.getSearch_id();
                    String target_type = "Alls Data";
                    Logs log = new Logs();
                    log.setUser(users);
                    log.setAction(action);
                    log.setAction_date(dataAcao);
                    log.setNew_value(newValue);
                    log.setOld_value(oldValue);
                    log.setTarget(target);
                    log.setTarget_type(target_type);
                    logsRepository.save(log);
                    // Fim Logs
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
                    allsDataRepository.save(allsData);
                } else {
                    AllsData newAllsData = new AllsData();
                    newAllsData.setNum_pendencias_financeiras(data.getNum_pendencias_financeiras());
                    newAllsData.setValor_pendencias_financeiras(data.getValor_pendencias_financeiras());
                    newAllsData.setNum_recuperacoes(data.getNum_recuperacoes());
                    newAllsData.setValor_recuperacoes(data.getValor_recuperacoes());
                    newAllsData.setNum_cheque_sem_fundo(data.getNum_cheque_sem_fundo());
                    newAllsData.setNum_protestos(data.getNum_protestos());
                    newAllsData.setValor_protestos(data.getValor_protestos());
                    newAllsData.setLimite_sugerido(data.getLimite_sugerido());
                    newAllsData.setNum_restricoes(data.getNum_restricoes());
                    newAllsData.setValor_restricoes(data.getValor_restricoes());
                    newAllsData.setProposal(proposal);
                    allsDataRepository.save(allsData);
                    // Logs
                    String action = "Register";
                    LocalDateTime dataAcao = LocalDateTime.now();
                    String target_type = "Alls Data";
                    Integer target = allsData.getSearch_id();
                    Logs log = new Logs();
                    log.setUser(users);
                    log.setAction(action);
                    log.setAction_date(dataAcao);
                    log.setTarget(target);
                    log.setTarget_type(target_type);
                    logsRepository.save(log);
                    // Fim Logs
                }
            }
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
