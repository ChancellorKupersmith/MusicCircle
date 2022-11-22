package com.service.music_circle_backend.services.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageServiceInterface {
    void init();
    void store(MultipartFile file);
    Path getPath(String filename);
//    Stream<Path> getAllPaths();
    Resource load(String filename) throws FileNotFoundException;
    void deleteAll();
}
