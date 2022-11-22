package com.service.music_circle_backend.services.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;

public class BASE64DecodedMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    String filename;

    public BASE64DecodedMultipartFile(byte[] imgContent,String filename) {
        this.imgContent = imgContent;
        this.filename = filename;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getOriginalFilename() {
        return filename;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public Resource getResource() {
        return null;
    }


    @Override
    public void transferTo(Path dest) throws IOException, IllegalStateException {

    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (OutputStream os = new FileOutputStream(dest)) {
            os.write(imgContent);
        }
    }
}

