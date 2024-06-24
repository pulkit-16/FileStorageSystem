package com.productservice.vcreatefilestoragesystem.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {

    private Path fileStoragePath;
    private String fileStorageLocation;

    public FileStorageService(@Value("${file.storage.location:temp}")String fileStorageLocation) {

        this.fileStorageLocation = fileStorageLocation;
        fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }
    public String storeFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        // create directory with file name

        // if filename already exists add
        Path filePath = Paths.get(fileStoragePath +"\\1\\"+fileName);
        try {
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("issue in storing file",e);
        }
        return fileName;
    }

    public String storeFileWithVersion(String existingFileName, MultipartFile file){

        Path filePath = generateFilePath(existingFileName);
        try {
            Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("issue in storing file",e);
        }
        return existingFileName;
    }
    private Path generateFilePath(String originalFileName) {
        String fileName = originalFileName;
        int version = 2;
        while (Files.exists(fileStoragePath.resolve(fileName))) {
            int extensionIndex = originalFileName.lastIndexOf('.');
            if (extensionIndex != -1) {
                fileName = originalFileName.substring(0, extensionIndex) + version + originalFileName.substring(extensionIndex);
            }
            version++;
        }
        return fileStoragePath.resolve(fileName);
    }
}
