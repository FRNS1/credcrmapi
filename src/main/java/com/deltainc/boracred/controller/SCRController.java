package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.ScrRegisterDTO;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.entity.SCR;
import com.deltainc.boracred.repositories.CustomerRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import com.deltainc.boracred.repositories.SCRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("api/v1/scr")
public class SCRController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    SCRRepository scrRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ScrRegisterDTO data){

        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
        Proposal proposal = optionalProposal.get();
        SCR scr = new SCR();
        scr.setVencer_valor_total(data.getVencer_valor_total());
        scr.setVencer_ate_30_dias_vencidos_ate_14_dias(data.getVencer_ate_30_dias_vencidos_ate_14_dias());
        scr.setVencer_31_60_dias(data.getVencer_31_60_dias());
        scr.setVencer_61_90_dias(data.getVencer_61_90_dias());
        scr.setVencer_181_360_dias(data.getVencer_181_360_dias());
        scr.setVencer_acima_360_dias(data.getVencer_acima_360_dias());
        scr.setVencer_indeterminado(data.getVencer_indeterminado());
        scr.setVencido_total(data.getVencido_total());
        scr.setVencido_15_30_dias(data.getVencido_15_30_dias());
        scr.setVencido_31_60_dias(data.getVencido_31_60_dias());
        scr.setVencido_61_90_dias(data.getVencido_61_90_dias());
        scr.setVencido_91_180_dias(data.getVencido_91_180_dias());
        scr.setVencido_181_360_dias(data.getVencido_181_360_dias());
        scr.setVencido_acima_360_dias(data.getVencido_acima_360_dias());
        scr.setPrejuizo_total(data.getPrejuizo_total());
        scr.setPrejuizo_ate_12_meses(data.getPrejuizo_ate_12_meses());
        scr.setPrejuizo_acima_12_meses(data.getPrejuizo_acima_12_meses());
        scr.setCoobrigacao_total(data.getCoobrigacao_total());
        scr.setCoobrigacao_assumida(data.getCoobrigacao_assumida());
        scr.setCoobrigacao_prestadas(data.getCoobrigacao_prestadas());
        scr.setCreditos_liberar_total(data.getCreditos_liberar_total());
        scr.setCreditos_liberar_ate_360_dias(data.getCreditos_liberar_ate_360_dias());
        scr.setCreditos_liberar_acima_360_dias(data.getCreditos_liberar_acima_360_dias());
        scr.setLimites_credito_valor_total(data.getLimites_credito_valor_total());
        scr.setLimites_credito_vencimento_ate_360_dias(data.getLimites_credito_vencimento_ate_360_dias());
        scr.setLimites_credito_vencimento_acima_360_dias(data.getLimites_credito_vencimento_acima_360_dias());
        scr.setProposal(proposal);
        scrRepository.save(scr);
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

}
