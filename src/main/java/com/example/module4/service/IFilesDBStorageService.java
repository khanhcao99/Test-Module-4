package com.example.module4.service;

import com.example.module4.model.FileDB;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public interface IFilesDBStorageService {
    public FileDB store(MultipartFile file) throws IOException;

    public Optional<FileDB> getFile(String idFile);

    public Stream<FileDB> getAllFiles();

    public void  deleteFile(String idFile);
}
