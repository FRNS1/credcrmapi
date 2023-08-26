package com.deltainc.boracred.controller;

import com.deltainc.boracred.dto.FilesUploadDTO;
import com.deltainc.boracred.entity.Files;
import com.deltainc.boracred.entity.Proposal;
import com.deltainc.boracred.entity.retrieveFileNameFromUploadService;
import com.deltainc.boracred.repositories.FilesRepository;
import com.deltainc.boracred.repositories.ProposalRepository;
import com.deltainc.boracred.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;

@Controller
@RequestMapping("api/v1/files")
public class FilesController {

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    FilesRepository filesRepository;

    retrieveFileNameFromUploadService fileNameWithMLGeneral = new retrieveFileNameFromUploadService();

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam(name = "file") MultipartFile file){
        String fileNameWithML = FileUploadService.uploadFile(file);
        fileNameWithMLGeneral.setFileName(fileNameWithML);
        return new ResponseEntity<>("Uploaded", HttpStatus.CREATED);
    }

    @PostMapping("/filesdata")
    public ResponseEntity filesDataRegister(@RequestBody FilesUploadDTO data){
        System.out.println(fileNameWithMLGeneral.getFileName());
        String fileName = fileNameWithMLGeneral.getFileName().replace(" ", "+");
        Optional<Proposal> optionalProposal = proposalRepository.findById(data.getProposal());
        Proposal proposal = optionalProposal.get();
        Files newFile = new Files();
        newFile.setProposal_id(proposal);
        newFile.setTipo_arquivo(data.getTipoArquivo());
        newFile.setFile_name(data.getFilaName());
        newFile.setUrl_arquivo("https://docsbora.s3.amazonaws.com/" + fileName);
        filesRepository.save(newFile);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

}
