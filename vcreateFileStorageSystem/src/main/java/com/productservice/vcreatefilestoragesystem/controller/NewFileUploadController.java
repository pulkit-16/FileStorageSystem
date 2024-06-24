package com.productservice.vcreatefilestoragesystem.controller;

import com.productservice.vcreatefilestoragesystem.Dto.FileUploadResponse;
import com.productservice.vcreatefilestoragesystem.services.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
public class NewFileUploadController {
    private FileStorageService fileStorageService;

    public NewFileUploadController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/newupload/")
    FileUploadResponse singleFileUpload(@RequestParam("file")MultipartFile file) throws IOException {
        String fileName = null;
        try {
            fileName = fileStorageService.storeFile(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String contentType = file.getContentType();

        ///http://localhost:8081/download/abc.jpg
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        FileUploadResponse response = new FileUploadResponse(fileName,contentType,url);

        return response;
    }
}
