package com.example.module4.service.impl;

import com.example.module4.model.TypeFile;
import com.example.module4.service.IFilesSVStorageService;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FilesSVStorageService implements IFilesSVStorageService {
    private TypeFile typeFile;
    private final Path root = Paths.get("uploads");

    public FilesSVStorageService() {
        typeFile = new TypeFile();
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        }catch (IOException e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public void save(List<MultipartFile> file) {
        if (Files.isDirectory(root)){
            try {
                for (MultipartFile files: file) {
                    Files.copy(files.getInputStream(), this.root.resolve(files.getOriginalFilename()));
                }
            }catch (IOException e){
                throw new RuntimeException("Could not save the file. Error: " + e.getMessage());
            }
        }else {
            init();
            try {
                for (MultipartFile files: file) {
                    Files.copy(files.getInputStream(), this.root.resolve(files.getOriginalFilename()));
                }
            }catch (IOException e){
                throw new RuntimeException("Could not save the file. Error: " + e.getMessage());
            }
        }
    }
    @Override
    public Resource load(String fileName) {
        try{
            Path file = root.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()){
                return resource;
            }else {
                throw new RuntimeException("Could not read the file!");
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        }catch (IOException e){
            throw new RuntimeException("Could not load the files!");
        }

    }

    @Override
    public boolean deleteFiles(File file) {
        return file.delete();
    }

    public TypeFile getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(TypeFile typeFile) {
        this.typeFile = typeFile;
    }
}
