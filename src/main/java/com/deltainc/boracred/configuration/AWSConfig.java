package com.deltainc.boracred.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Configuration
public class AWSConfig {

    public static AWSCredentials credentials(){
        AWSCredentials credentials = new BasicAWSCredentials(
                "AKIA5A3V4D2QC4GZSSLS",
                "omFHQzFO8JiMwLK+GD8gofl53OTREObqagIyT+JX"
        );
        return credentials;
    }

    @Bean
    public static AmazonS3 amazonS3() {
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .withRegion(Regions.US_EAST_1)
                .build();

        return s3Client;
    }

}
