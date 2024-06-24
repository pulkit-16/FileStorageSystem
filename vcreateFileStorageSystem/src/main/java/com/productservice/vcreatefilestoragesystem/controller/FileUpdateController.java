package com.productservice.vcreatefilestoragesystem.controller;

import com.productservice.vcreatefilestoragesystem.Dto.FileUploadResponse;
import com.productservice.vcreatefilestoragesystem.services.FileStorageService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@RestController
public class FileUpdateController {
    private FileStorageService fileStorageService;

    public FileUpdateController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }
    @PutMapping("/update")
    public FileUploadResponse updateFile(@RequestParam("filename") String existingFileName,
                                         @RequestParam("file") MultipartFile file) throws IOException {
        String fileName;
        fileName = fileStorageService.storeFileWithVersion(existingFileName, file);

        String contentType = file.getContentType();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/update/")
                .path(fileName)
                .toUriString();

        return new FileUploadResponse(fileName, contentType, url);
    }
}

