package com.db.GroupSequencerDB_REST_.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Service
public class AudioFileUploadService {
    public void uploadAudioFile(MultipartFile multipartFile) throws IOException {
        multipartFile.transferTo(new File("C:\\Users\\kuper\\Desktop\\Android_Apps\\CS_309\\GroupSequencerDB_REST_\\GroupSequencerDB_REST_\\src\\main\\resources\\mp3Files\\"+multipartFile.getOriginalFilename()));
    }
}