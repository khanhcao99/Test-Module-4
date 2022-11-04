package com.example.module4;

import com.example.module4.service.impl.FilesSVStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class SpringBootUploadFilesApplication implements CommandLineRunner {
    @Resource
    FilesSVStorageService filesSVStorageService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootUploadFilesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        filesSVStorageService.init();
    }

}
