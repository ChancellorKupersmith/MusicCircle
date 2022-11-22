package com.service.music_circle_backend.created_properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "audio")
public class AudioFileStorageProperty {
    private String uploadDirectory;

    public String getUploadDirectory(){ return uploadDirectory; }
    public void setUploadDirectory(String uploadDirectory) { this.uploadDirectory = uploadDirectory; }
}
