package com.deltainc.boracred.controller;

import com.amazonaws.Response;
import com.deltainc.boracred.dto.AnalyticsRegisterDTO;
import com.deltainc.boracred.dto.AnalyticsUpdateDTO;
import com.deltainc.boracred.entity.Analytics;
import com.deltainc.boracred.entity.Customer;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.entity.Users;
import com.deltainc.boracred.repositories.AnalyticsRepository;
import com.deltainc.boracred.repositories.CustomerRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import com.deltainc.boracred.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody AnalyticsRegisterDTO data){
        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal());
        Optional<Customer> optionalCustomer = customerRepository.findById(data.getCustomer());
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
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody AnalyticsUpdateDTO data){
        try{
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposalId());
            if (optionalProposal.isPresent()){
                Proposal proposal = optionalProposal.get();
                Analytics analytics = analyticsRepository.findByProposal(proposal);
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
