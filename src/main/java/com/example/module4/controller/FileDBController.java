package com.example.module4.controller;

import com.example.module4.model.FileDB;
import com.example.module4.model.ResponseMessageFile;
import com.example.module4.model.ResposeFileDB;
import com.example.module4.model.TypeFile;
import com.example.module4.service.impl.FilesDBStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/files")
public class FileDBController {
    @Autowired
    private FilesDBStorageService filesStorageService;

    @PostMapping
    public ResponseEntity<ResponseMessageFile> uploadFiles(@RequestParam("file") List<MultipartFile> file) {
        String message = "";
        boolean allowed = true;
        int sizeListFile = file.size();
        for (int i = 0; i < sizeListFile; i++) {
            if (!filesStorageService.getTypeFile().getAllowedMimeTypes().contains(file.get(i).getContentType())) {
                allowed = false;
                break;
            }
        }
        if (allowed) {
            try {
                for (MultipartFile item : file) {
                    filesStorageService.store(item);
                }
                message = "Uploaded the files successfully: ";
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageFile(message));
            } catch (Exception e) {
                message = "Could not upload file: ";
                return new ResponseEntity<>(new ResponseMessageFile(message), HttpStatus.EXPECTATION_FAILED);
            }
        }
        message = "This file format is not allowed to be uploaded!";
        return new ResponseEntity<>(new ResponseMessageFile(message), HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping
    public ResponseEntity<List<ResposeFileDB>> getListFiles() {
        List<ResposeFileDB> files = filesStorageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files")
                    .path(dbFile.getId())
                    .toUriString();

            return new ResposeFileDB(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.ok().body(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable("id") String id) {
        Optional<FileDB> fIleDB = filesStorageService.getFile(id);
        return fIleDB.map(fileDB -> ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData())).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageFile> deleteFile(@PathVariable("id") String id){
        Optional<FileDB> fileDB = filesStorageService.getFile(id);
        if (fileDB.isPresent()){
            filesStorageService.deleteFile(id);
            return ResponseEntity.ok().body(new ResponseMessageFile("Delete file successfully!"));
        }
        return new ResponseEntity<>(new ResponseMessageFile("Delete failed, file does not exist!"), HttpStatus.NOT_FOUND);
    }

}
