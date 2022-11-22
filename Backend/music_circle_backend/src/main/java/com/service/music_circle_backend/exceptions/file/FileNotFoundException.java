package com.service.music_circle_backend.exceptions.file;

public class FileNotFoundException extends FileException {
    public FileNotFoundException(String message) {
        super(message);
    }
    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
