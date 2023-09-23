package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.dto.AnalyticsRegisterDTO;
import com.deltainc.boracred.dto.AnalyticsUpdateDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/analytics")
public class AnalyticsController {

    @Autowired
    AnalyticsRepository analyticsRepository;

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UsersRepository usersRepository;


    @Autowired
    LogsRepository logsRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AnalyticsRegisterDTO data){
        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal());
        Optional<Customer> optionalCustomer = customerRepository.findById(data.getCustomer());
        Optional<Users> optionalUsers = usersRepository.findById(data.getUser_id());
        Users users = optionalUsers.get();
        Analytics analytics = new Analytics();
        analytics.setProposal(optionalProposal.get());
        analytics.setCustomer(optionalCustomer.get());
        analytics.setNum_titulos_protestados(data.getNum_titulos_protestados());
        analytics.setScore(data.getScore());
        analytics.setNum_refins(data.getNum_refins());
        analytics.setValor_cadins(data.getValor_cadins());
        analytics.setValor_iss(data.getValor_iss());
        analytics.setNum_processos(data.getNum_processos());
        analytics.setValor_processos(data.getValor_processos());
        analytics.setNum_uf_processos(data.getNum_uf_processos());
        analytics.setDivida_ativa(data.getDivida_ativa());
        analytics.setValor_titulos_protestados(data.getValor_titulos_protestados());
        analytics.setRisco(data.getRisco());
        analytics.setPep(data.isPep());
        analytics.setNum_cheques_devolvidos(data.getNum_cheques_devolvidos());
        analytics.setValor_cheques_devolvidos(data.getValor_cheques_devolvidos());
        analytics.setValor_pefins(data.getValor_pefins());
        analytics.setNum_pefins(data.getNum_pefins());
        analytics.setEmpresas_nao_informadas(data.getEmpresas_nao_informadas());
        analyticsRepository.save(analytics);
        // LOGS
        String action = "Update";
        LocalDateTime dataAcao = LocalDateTime.now();
        Integer target = analytics.getAnalytics_id();
        String target_type = "Analytics";
        Logs log = new Logs();
        log.setUser(users);
        log.setAction(action);
        log.setAction_date(dataAcao);
        log.setTarget(target);
        log.setTarget_type(target_type);
        logsRepository.save(log);
        // FIM LOGS
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody AnalyticsUpdateDTO data){
        try{
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposalId());
            Optional<Users> optionalUsers = usersRepository.findById(data.getUser_id());
            Users users = optionalUsers.get();
            if (optionalProposal.isPresent()){
                Proposal proposal = optionalProposal.get();
                Analytics analytics = analyticsRepository.findByProposal(proposal);
                // Logs
                String action = "Update";
                LocalDateTime dataAcao = LocalDateTime.now();
                String newValue = "num_titulos_protestados = " + data.getNumTitulosProtestados() + ", score = " + data.getScore() + ", num_refins = " + data.getNumRefins() + ", valor_cadins = " + data.getValorCadins() + ", valor_iss = " + data.getValorIss() +
                        ", num_processos = " + data.getNumProcessos() + ", num_uf_processos = " + data.getNumUfProcessos() + ", divida_ativa = " + data.getDividaAtiva() + ", valor_titulos_protestados = " + data.getValorTitulosProtestados() + ", risco = " + data.getRisco() +
                        ", is_pep = " + data.isPep() + ", num_cheques_devolvidos = " + data.getNumChequesDevolvidos() + ", valor_cheques_devolvidos = " + data.getValorChequesDevolvidos() + ", valor_pefins = " + data.getValorPefins() + ", num_pefins = " + data.getNumPefins() + ", empresas_nao-informadas = " + data.getEmpresasNaoInformadas();
                String oldValue = "num_titulos_protestados = " + analytics.getNum_titulos_protestados() + ", score = " + analytics.getScore() + ", num_refins = " + analytics.getNum_refins() + ", valor_cadins = " + analytics.getValor_cadins() + ", valor_iss = " + analytics.getValor_iss() +
                        ", num_processos = " + analytics.getNum_processos() + ", num_uf_processos = " + analytics.getNum_uf_processos() + ", divida_ativa = " + analytics.getDivida_ativa() + ", valor_titulos_protestados = " + analytics.getValor_titulos_protestados() + ", risco = " + analytics.getRisco() +
                        ", is_pep = " + analytics.isPep() + ", num_cheques_devolvidos = " + analytics.getNum_cheques_devolvidos() + ", valor_cheques_devolvidos = " + analytics.getValor_cheques_devolvidos() + ", valor_pefins = " + analytics.getValor_pefins() + ", num_pefins = " + analytics.getNum_pefins() + ", empresas_nao-informadas = " + analytics.getEmpresas_nao_informadas();
                Integer target = analytics.getAnalytics_id();
                String target_type = "Analytics";
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
                analytics.setNum_titulos_protestados(data.getNumTitulosProtestados());
                analytics.setScore(data.getScore());
                analytics.setNum_refins(data.getNumRefins());
                analytics.setValor_cadins(data.getValorCadins());
                analytics.setValor_iss(data.getValorIss());
                analytics.setNum_processos(data.getNumProcessos());
                analytics.setValor_processos(data.getValorProcessos());
                analytics.setNum_uf_processos(data.getNumUfProcessos());
                analytics.setDivida_ativa(data.getDividaAtiva());
                analytics.setValor_titulos_protestados(data.getValorTitulosProtestados());
                analytics.setRisco(data.getRisco());
                analytics.setPep(data.isPep());
                analytics.setNum_cheques_devolvidos(data.getNumChequesDevolvidos());
                analytics.setValor_cheques_devolvidos(data.getValorChequesDevolvidos());
                analytics.setValor_pefins(data.getValorPefins());
                analytics.setNum_pefins(data.getNumPefins());
                analytics.setEmpresas_nao_informadas(data.getEmpresasNaoInformadas());
                analyticsRepository.save(analytics);

            }
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
