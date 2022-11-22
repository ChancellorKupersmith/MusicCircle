package com.service.music_circle_backend.services.audio_file;

import java.io.InputStream;

public interface FileUploadInterface {
    public void uploadSftpFromInputStream(InputStream file, String sftpFileName);
}
