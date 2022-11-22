package com.service.music_circle_backend.services.imageupload;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;
import java.io.InputStream;

@Service
public class UploadFileImpl {

    public static final String NETID = "iaroy";
    public static final String PASSWORD = "";
    public static final String HOST = "coms-309-004.cs.iastate.edu";

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession(NETID, HOST);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.setPassword(PASSWORD);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    public void uploadSftpFromInputStream(InputStream file, String sftpFileName) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = setupJsch();
        } catch (JSchException e) {
            // throw the exception
        }
        try {
            assert channelSftp != null;
            channelSftp.connect();
        } catch (JSchException e) {
            // throw the exception
        }
        try{
            channelSftp.put(file, "/var/www/img_uploads/"+sftpFileName);
            System.out.println("Upload Complete");
        } catch (SftpException e) {
            // throw the exception
        }
        channelSftp.exit();
    }

}

