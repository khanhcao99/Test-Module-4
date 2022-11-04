package com.example.module4.controller;

import com.example.module4.model.FileServer;
import com.example.module4.model.ResponseMessageFile;
import com.example.module4.model.TypeFile;
import com.example.module4.service.impl.FilesSVStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/files/server")
public class FileSVController {
    @Autowired
    private FilesSVStorageService storageService;

    @PostMapping
    public ResponseEntity<ResponseMessageFile> uploadFile(@RequestParam("file")List<MultipartFile> file){
        String message = "";
        boolean allowed = true;
        for (MultipartFile multipartFile: file) {
            if (!storageService.getTypeFile().getAllowedMimeTypes().contains(multipartFile.getContentType())){
                allowed = false;
                break;
            }
        }
        if (allowed){
            try{
                storageService.save(file);
                message = "Uploaded the file successfully!";
                return ResponseEntity.ok().body(new ResponseMessageFile(message));
            }catch (RuntimeException e){
                message = "Error uploading duplicate files!";
                return new ResponseEntity<>(new ResponseMessageFile(message), HttpStatus.EXPECTATION_FAILED);
            }
        }
        message = "This files format is not allowed to be uploaded!";
        return new ResponseEntity<>(new ResponseMessageFile(message), HttpStatus.EXPECTATION_FAILED);
    }

    @GetMapping
    public ResponseEntity<List<FileServer>> getListFiles(){
        List<FileServer> fileServers = storageService.loadAll().map(path -> {
            String fileName = path.getFileName().toString();
            String url = MvcUriComponentsBuilder.fromMethodName(FileSVController.class, "getFile", path.getFileName().toString()).build().toString();
            int index = fileName.lastIndexOf(".");
            String typeFile = "";
            if (index > 0){
               typeFile = fileName.substring(index + 1);
            }
            return new FileServer(fileName, url, typeFile);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(fileServers, HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName){
         Resource file = storageService.load(fileName);
         return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                 "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @DeleteMapping("{path}")
    public ResponseEntity<ResponseMessageFile> deleteFiles(@PathVariable("path") String path){
        File file = new File("D:\\module-4\\uploads\\" + path);
        if (file.exists()){
            storageService.deleteFiles(file);
            return ResponseEntity.ok().body(new ResponseMessageFile("Delete file successfully!"));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessageFile("Delete failed, file does not exist!"));
    }
}
