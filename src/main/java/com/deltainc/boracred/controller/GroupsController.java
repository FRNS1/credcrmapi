package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.GroupRegisterDTO;
import com.deltainc.boracred.entity.Grupos;
import com.deltainc.boracred.repositories.GruposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/groups")
public class GroupsController {

    @Autowired
    GruposRepository gruposRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody GroupRegisterDTO data){
        Grupos grupo = new Grupos();
        grupo.setNome_grupo(data.getNome_grupo());
        gruposRepository.save(grupo);
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity getAll(){
        return new ResponseEntity<>(gruposRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity getById(@PathVariable Integer id){
        return new ResponseEntity<>(gruposRepository.findById(id), HttpStatus.OK);
    }

}
