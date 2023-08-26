package com.deltainc.boracred.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilesUploadDTO {

    private String tipoArquivo;

    private Integer proposal;

    private String url;

    private String filaName;


}
