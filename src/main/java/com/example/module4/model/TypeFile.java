package com.example.module4.model;

import java.util.ArrayList;
import java.util.List;

public class TypeFile {
    private List<String> allowedMimeTypes = new ArrayList<>();

    public TypeFile() {
        allowedMimeTypes.add("image/jpeg");
        allowedMimeTypes.add("image/png");
        allowedMimeTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    public List<String> getAllowedMimeTypes() {
        return allowedMimeTypes;
    }

    public void setAllowedMimeTypes(List<String> allowedMimeTypes) {
        this.allowedMimeTypes = allowedMimeTypes;
    }
}
