package com.service.music_circle_backend.services.file;

import com.service.music_circle_backend.created_properties.AudioFileStorageProperty;
import com.service.music_circle_backend.exceptions.file.FileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AudioFileStorageService implements FileStorageServiceInterface{
    private final Path audiofileStorageLocation;
    @Autowired
    public AudioFileStorageService(AudioFileStorageProperty audioFileStorageProperty){
        this.audiofileStorageLocation = Paths.get(audioFileStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
    }

    @Override
    public void init() {
        if(!(this.audiofileStorageLocation.toFile().exists())){
            try{
                Files.createDirectory(this.audiofileStorageLocation);
            }catch (Exception e){
                throw new FileException("Could not initialize audio file storage location", e);
            }
        }
    }

    @Override
    public void store(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(), this.audiofileStorageLocation.resolve(file.getOriginalFilename()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Path getPath(String filename) {
        return audiofileStorageLocation.resolve(filename);
    }

    @Override
    public Resource load(String filename) throws FileNotFoundException {
        try{
            Path file = getPath(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                return resource;
            }
            else {
                throw new FileNotFoundException("Could not read file: " + filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("Could not read file: " + filename);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(audiofileStorageLocation.toFile());
    }
}
