package com.service.music_circle_backend.services.file;

import com.service.music_circle_backend.created_properties.PicFileStorageProperty;
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
import java.util.Objects;

@Service
public class PicFileStorageService implements FileStorageServiceInterface{
    private final Path picFileStorageLocation;
    @Autowired
    public PicFileStorageService(PicFileStorageProperty picFileStorageProperty){
        this.picFileStorageLocation = Paths.get(picFileStorageProperty.getUploadDirectory()).toAbsolutePath().normalize();
    }
    @Override
    public void init() {
        if(!(this.picFileStorageLocation.toFile().exists())){
            try{
                Files.createDirectory(this.picFileStorageLocation);
            }catch (Exception e){
                throw new FileException("Could not initialize audio file storage location", e);
            }
        }
    }

    @Override
    public void store(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(), this.picFileStorageLocation.resolve(file.getOriginalFilename()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public Boolean storeImg(MultipartFile file) {
        try{
            Files.copy(file.getInputStream(), this.picFileStorageLocation.resolve(Objects.requireNonNull(file.getOriginalFilename())));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Path getPath(String filename) {
        return picFileStorageLocation.resolve(filename);
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
    public void deleteAll() { FileSystemUtils.deleteRecursively(picFileStorageLocation.toFile()); }
}
