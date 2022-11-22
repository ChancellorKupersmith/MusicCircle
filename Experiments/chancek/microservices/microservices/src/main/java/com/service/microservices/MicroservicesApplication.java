package com.service.microservices;

import com.service.microservices.entities.audio_file.AudioFile;
import com.service.microservices.entities.playlist.Playlist;
import com.service.microservices.entities.user.User;
import com.service.microservices.repos.audio_file.AudioFileRepository;
import com.service.microservices.repos.playlist.PlaylistRepository;
import com.service.microservices.repos.user.UserRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
public class MicroservicesApplication {

//	private static final Logger log = LoggerFactory.getLogger(MicroservicesApplication.class);
//	@Autowired
//	private AudioFileRepository audioFileRepository;
//	@Autowired
//	private PlaylistRepository playlistRepository;
//	@Autowired UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(MicroservicesApplication.class, args);
	}

//	@Bean
//	@Test
//	public void whenCalledSave_thenCorrectNumberOfAudioFiles(){
//		audioFileRepository.save(new AudioFile("banana.mp3", 1L));
//		List<AudioFile> audioFiles = (List<AudioFile>) audioFileRepository.findAll();
//		log.info("");
//		log.info("Audio Files List Size: " + String.valueOf(audioFiles.size()));
//		log.info("");
//
//	}
//
//	@Bean
//	@Test
//	public void whenCalledSave_thenCorrectNumberOfPlaylists(){
//		playlistRepository.save(new Playlist("Liked", 1L));
//		List<Playlist> playlists = (List<Playlist>) playlistRepository.findAll();
//		log.info("");
//		log.info("Playlist List Size: " + String.valueOf(playlists.size()));
//		log.info("");
//
//	}
//
//	@Bean
//	@Test
//	public void whenCalledSave_thenCorrectNumberOfUsers(){
//		userRepository.save(new User());
//		List<User> users = (List<User>) userRepository.findAll();
//		log.info("");
//		log.info("Users List Size: " + String.valueOf(users.size()));
//		log.info("");
//
//	}
}
