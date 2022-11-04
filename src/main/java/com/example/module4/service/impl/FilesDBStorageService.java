package com.example.module4.service.impl;

import com.example.module4.model.FileDB;
import com.example.module4.model.TypeFile;
import com.example.module4.repository.FileDBRepository;
import com.example.module4.service.IFilesDBStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FilesDBStorageService implements IFilesDBStorageService {
    private TypeFile typeFile;
    @Autowired
    private FileDBRepository fileDBRepository;

    public FilesDBStorageService() {
        typeFile = new TypeFile();
    }
    @Override
    public FileDB store(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fIleDB = new FileDB(filename, file.getContentType(), file.getBytes());
        return fileDBRepository.save(fIleDB);
    }

    @Override
    public Optional<FileDB> getFile(String idFile) {
        return fileDBRepository.findById(idFile);
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    @Override
    public void deleteFile(String idFile) {
        fileDBRepository.deleteById(idFile);
    }


    public TypeFile getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(TypeFile typeFile) {
        this.typeFile = typeFile;
    }
}
