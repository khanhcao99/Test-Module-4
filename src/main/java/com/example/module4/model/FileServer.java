package com.example.module4.model;

public class FileServer {
    private String name;
    private String url;

    private String typeFile;

    public FileServer() {

    }

    public FileServer(String name, String url, String typeFile) {
        this.name = name;
        this.url = url;
        this.typeFile = typeFile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }
}
