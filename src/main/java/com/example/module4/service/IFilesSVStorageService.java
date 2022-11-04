package com.example.module4.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface IFilesSVStorageService {
    public void init();

    public void save(List<MultipartFile> file);

    public Resource load(String fileName);

    public void deleteAll();

    public Stream<Path> loadAll();

    public boolean deleteFiles(File file);

}
