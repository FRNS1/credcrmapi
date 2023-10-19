package com.deltainc.boracred.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.deltainc.boracred.configuration.AWSConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class FileUploadService {


    public static String uploadFile(MultipartFile file){
        AmazonS3 s3Client = AWSConfig.amazonS3();
        File fileObj = convertMultiPartFiletoFile(file);
        String fileName = "documentos/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest("docsbora", fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    public static String uploadFileContrato(MultipartFile file){
        AmazonS3 s3Client = AWSConfig.amazonS3();
        File fileObj = convertMultiPartFiletoFile(file);
        String fileName = "contratos/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest("docsbora", fileName, fileObj));
        fileObj.delete();
        return fileName;
    }

    private static File convertMultiPartFiletoFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e){
            System.out.println(e);
        }
        return convertedFile;
    }

}
