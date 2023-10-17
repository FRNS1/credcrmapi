package com.deltainc.boracred.controller;

import com.deltainc.boracred.entity.FormXP;
import com.deltainc.boracred.repositories.FormXpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/formxp")
public class FormXpController {

    @Autowired
    FormXpRepository formXpRepository;

    @PostMapping("/register")
    public ResponseEntity register(FormXP data){
        try {
            FormXP form = new FormXP();
            form.setNome_completo(data.getNome_completo());
            formXpRepository.save(data);
            return new ResponseEntity<>("Created", HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
