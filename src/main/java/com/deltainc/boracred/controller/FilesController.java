package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.FilesUploadDTO;
import com.deltainc.boracred.entity.*;
import com.deltainc.boracred.repositories.FilesRepository;
import com.deltainc.boracred.repositories.LogsRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import com.deltainc.boracred.repositories.UsersRepository;
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
@RequestMapping("api/v1/files")
public class FilesController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    FilesRepository filesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    LogsRepository logsRepository;

    retrieveFileNameFromUploadService fileNameWithMLGeneral = new retrieveFileNameFromUploadService();

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam(name = "file") MultipartFile file){
        String fileNameWithML = FileUploadService.uploadFile(file);
        System.out.println(file);
        fileNameWithMLGeneral.setFileName(fileNameWithML);
        return new ResponseEntity<>("Uploaded", HttpStatus.CREATED);
    }

    @PostMapping("/filesdata")
    public ResponseEntity filesDataRegister(@RequestBody FilesUploadDTO data){
        try {
            System.out.println(data.getProposal());
            System.out.println(fileNameWithMLGeneral.getFileName());
            String fileName = fileNameWithMLGeneral.getFileName().replace(" ", "+");
            Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal());
            System.out.println(optionalProposal + " ACHEEEEEEEEI");
            Proposal proposal = optionalProposal.get();
            System.out.println(proposal + " ACHEEEEEEEEI");
            Optional<Users> optionalUsers = usersRepository.findById(data.getUser_id());
            Users users = optionalUsers.get();
            Files newFile = new Files();
            newFile.setProposal(proposal);
            newFile.setTipo_arquivo(data.getTipoArquivo());
            newFile.setFile_name(data.getFilaName());
            newFile.setUrl_arquivo("https://docsbora.s3.amazonaws.com/" + fileName);
            filesRepository.save(newFile);
            // LOGS
            String action = "Register";
            LocalDateTime dataAcao = LocalDateTime.now();
            Integer target = newFile.getFiles_id();
            String target_type = "Files";
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
