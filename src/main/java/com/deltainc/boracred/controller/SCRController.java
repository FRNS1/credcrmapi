package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.ScrRegisterDTO;
import com.deltainc.boracred.dto.ScrUpdateDTO;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.entity.SCR;
import com.deltainc.boracred.repositories.CustomerRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import com.deltainc.boracred.repositories.SCRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
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
        scr.setVencer_91_180_dias(data.getVencer_91_180_dias());
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

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody ScrUpdateDTO data){
        try{
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal_id());
            if (optionalProposal.isPresent()){
                Proposal proposal = optionalProposal.get();
                SCR scr = scrRepository.findByProposal(proposal);
                if (scr != null) {
                    System.out.println(scr);
                    scr.setVencer_valor_total(data.getVencer_valor_total());
                    scr.setVencer_ate_30_dias_vencidos_ate_14_dias(data.getVencer_ate_30_dias_vencidos_ate_14_dias());
                    scr.setVencer_31_60_dias(data.getVencer_31_60_dias());
                    scr.setVencer_61_90_dias(data.getVencer_61_90_dias());
                    scr.setVencer_91_180_dias(data.getVencer_91_180_dias());
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
                    scrRepository.save(scr);
                } else {
                    System.out.println("scr");
                    SCR newScr = new SCR();
                    newScr.setVencer_valor_total(data.getVencer_valor_total());
                    newScr.setVencer_ate_30_dias_vencidos_ate_14_dias(data.getVencer_ate_30_dias_vencidos_ate_14_dias());
                    newScr.setVencer_31_60_dias(data.getVencer_31_60_dias());
                    newScr.setVencer_61_90_dias(data.getVencer_61_90_dias());
                    newScr.setVencer_91_180_dias(data.getVencer_91_180_dias());
                    newScr.setVencer_181_360_dias(data.getVencer_181_360_dias());
                    newScr.setVencer_acima_360_dias(data.getVencer_acima_360_dias());
                    newScr.setVencer_indeterminado(data.getVencer_indeterminado());
                    newScr.setVencido_total(data.getVencido_total());
                    newScr.setVencido_15_30_dias(data.getVencido_15_30_dias());
                    newScr.setVencido_31_60_dias(data.getVencido_31_60_dias());
                    newScr.setVencido_61_90_dias(data.getVencido_61_90_dias());
                    newScr.setVencido_91_180_dias(data.getVencido_91_180_dias());
                    newScr.setVencido_181_360_dias(data.getVencido_181_360_dias());
                    newScr.setVencido_acima_360_dias(data.getVencido_acima_360_dias());
                    newScr.setPrejuizo_total(data.getPrejuizo_total());
                    newScr.setPrejuizo_ate_12_meses(data.getPrejuizo_ate_12_meses());
                    newScr.setPrejuizo_acima_12_meses(data.getPrejuizo_acima_12_meses());
                    newScr.setCoobrigacao_total(data.getCoobrigacao_total());
                    newScr.setCoobrigacao_assumida(data.getCoobrigacao_assumida());
                    newScr.setCoobrigacao_prestadas(data.getCoobrigacao_prestadas());
                    newScr.setCreditos_liberar_total(data.getCreditos_liberar_total());
                    newScr.setCreditos_liberar_ate_360_dias(data.getCreditos_liberar_ate_360_dias());
                    newScr.setCreditos_liberar_acima_360_dias(data.getCreditos_liberar_acima_360_dias());
                    newScr.setLimites_credito_valor_total(data.getLimites_credito_valor_total());
                    newScr.setLimites_credito_vencimento_ate_360_dias(data.getLimites_credito_vencimento_ate_360_dias());
                    newScr.setLimites_credito_vencimento_acima_360_dias(data.getLimites_credito_vencimento_acima_360_dias());
                    newScr.setProposal(proposal);
                    scrRepository.save(scr);
                }
            }
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
    }
}
