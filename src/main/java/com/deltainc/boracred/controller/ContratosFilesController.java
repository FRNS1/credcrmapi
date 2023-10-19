package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.ContratosFilesUploadDTO;
import com.deltainc.boracred.dto.FilesUploadDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.*;
import com.deltainc.boracred.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.Optional;


@Controller
@RequestMapping("api/v1/contratosfiles")
public class ContratosFilesController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    ContratosFilesRepository contratosFilesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LogsRepository logsRepository;

    retrieveFileNameFromUploadService fileNameWithMLGeneral = new retrieveFileNameFromUploadService();

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam(name = "file") MultipartFile file){
        String fileNameWithML = FileUploadService.uploadFileContrato(file);
        System.out.println(file);
        fileNameWithMLGeneral.setFileName(fileNameWithML);
        return new ResponseEntity<>("Uploaded", HttpStatus.CREATED);
    }

    @PostMapping("/filesdata")
    public ResponseEntity filesDataRegister(@RequestBody ContratosFilesUploadDTO data){
        try {
            System.out.println(fileNameWithMLGeneral.getFileName());
            String fileName = fileNameWithMLGeneral.getFileName().replace(" ", "+");
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal());
            System.out.println(optionalProposal + " ACHEEEEEEEEI");
            Proposal proposal = optionalProposal.get();
            System.out.println(proposal + " ACHEEEEEEEEI");
            Optional<Users> optionalUsers = usersRepository.findById(data.getUser_id());
            Users users = optionalUsers.get();
            ContratosFiles newFile = new ContratosFiles();
            newFile.setProposal(proposal);
            newFile.setPath_file("https://docsbora.s3.amazonaws.com/" + fileName);
            contratosFilesRepository.save(newFile);
            // LOGS
            String action = "Register";
            LocalDateTime dataAcao = LocalDateTime.now();
            Integer target = newFile.getContratos_files_id();
            String target_type = "Contratos Files";
            Logs log = new Logs();
            log.setUser(users);
            log.setAction(action);
            log.setAction_date(dataAcao);
            log.setTarget(target);
            log.setTarget_type(target_type);
            logsRepository.save(log);
            // FIM LOGS
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
