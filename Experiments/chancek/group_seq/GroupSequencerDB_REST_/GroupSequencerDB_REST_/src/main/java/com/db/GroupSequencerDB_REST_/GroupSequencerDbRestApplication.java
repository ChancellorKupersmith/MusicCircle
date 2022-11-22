package com.db.GroupSequencerDB_REST_;


import com.db.GroupSequencerDB_REST_.entities.AudioFile;
import com.db.GroupSequencerDB_REST_.properties.FileUploadProperties;
import com.db.GroupSequencerDB_REST_.repositories.AudioFileRepository;
import com.db.GroupSequencerDB_REST_.storage.StorageProperties;
import com.db.GroupSequencerDB_REST_.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.db.GroupSequencerDB_REST_.storage"})
@EnableConfigurationProperties({StorageProperties.class, FileUploadProperties.class})
public class GroupSequencerDbRestApplication {
	private static final Logger log = LoggerFactory.getLogger((GroupSequencerDbRestApplication.class));
	public static void main(String[] args) {
		SpringApplication.run(GroupSequencerDbRestApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(StorageService storageService){
//		return (args) -> {
//			storageService.deleteAll();
//			storageService.init();
//		};
//	}

//	@Bean
//	public CommandLineRunner demoAudioFiles(AudioFileRepository audioFileRepository){
//		return (args) -> {
//			//save AudioFiles to repository
//			audioFileRepository.save(new AudioFile("banana.mp3"));
//			audioFileRepository.save(new AudioFile("blackjack.mp3"));
//			audioFileRepository.save(new AudioFile("dr.whoever.mp3"));
//			audioFileRepository.save(new AudioFile("hero.mp3", 1));
//			audioFileRepository.save(new AudioFile("modest.mp3", 1.5));
//			audioFileRepository.save(new AudioFile("park.mp3", 2.3));
//			audioFileRepository.save(new AudioFile("pressure_in_my_palms.mp3", 1.5));
//			audioFileRepository.save(new AudioFile("smile.mp3", 16.2));
//
//			//fetch all AudioFiles
//			log.info("Audio Files found with findAll():");
//			log.info("---------------------------------");
//			for(AudioFile audioFile : audioFileRepository.findAll()){
//				log.info(audioFile.toString());
//			}
//			log.info("");
//
//			//fetch AudioFile by ID
//			AudioFile audioFile = audioFileRepository.findAudioFileById(1L);
//			log.info("Audio File found with findByID(1L):");
//			log.info("-----------------------------------");
//			log.info(audioFile.toString());
//			log.info("");
//
//			//fetch AudioFile by filename
//			log.info("Audio Files found with findByFileName(park.mp3):");
//			log.info("--------------------------------------");
//			audioFileRepository.findAudioFileByFilename("park.mp3").forEach(file ->{log.info(file.toString());});
//			log.info("");
//
//			//fetch AudioFiles by barLocation
//			log.info("Audio Files found with findByBarLocation(0):");
//			log.info("-----------------------------------------");
//			audioFileRepository.findAudioFileByBarLocation(0).forEach(file -> {log.info(file.toString());});
//			log.info("");
//			log.info("Audio Files found with findByBarLocation(1.5):");
//			log.info("-----------------------------------------");
//			audioFileRepository.findAudioFileByBarLocation(1.5).forEach(file -> {log.info(file.toString());});
//			log.info("");
//		};
//	}

//	@Bean
//	CommandLineRunner demoComments(CommentRepository commentRepository){
//		return (args) -> {
//			//save comments
//			commentRepository.save(new Comment("Someone needs to mix this", 2));
//			commentRepository.save(new Comment("Really like this part", 2));
//			commentRepository.save(new Comment("How did you do this part?", 3));
//			commentRepository.save(new Comment("Is Everyone cool to post?", 0));
//
//			//fetch all comments
//			log.info("Comments found with findAll():");
//			log.info("------------------------------");
//			for(Comment comment : commentRepository.findAll()){
//				log.info(comment.toString());
//			}
//			log.info("");
//
//			//fetch an individual comment by ID
//			Comment comment = commentRepository.findById(2L);
//			log.info("Comment found with findbyId(2L):");
//			log.info("--------------------------------");
//			log.info(comment.toString());
//			log.info("");
//
//			//fetch Comments by barLocation
//			log.info("Comment found with findByBarLocation(2)");
//			log.info("---------------------------------------");
//			commentRepository.findCommentsByBarLocation(2).forEach(comment1 -> {
//				log.info(comment1.toString());
//			});
//			log.info("");
//		};
//	}

//	@Bean
//	public CommandLineRunner demoSavedState(SongStateRepository songStateRepository, AudioFileRepository audioFileRepository, CommentRepository commentRepository){
//			//save AudioFiles to repository
//			audioFileRepository.save(new AudioFile("banana.mp3"));
//			audioFileRepository.save(new AudioFile("blackjack.mp3"));
//			audioFileRepository.save(new AudioFile("dr.whoever.mp3"));
//			audioFileRepository.save(new AudioFile("hero.mp3", 1));
//			audioFileRepository.save(new AudioFile("modest.mp3", 1.5));
//			audioFileRepository.save(new AudioFile("park.mp3", 2.3));
//			audioFileRepository.save(new AudioFile("pressure_in_my_palms.mp3", 1.5));
//			audioFileRepository.save(new AudioFile("smile.mp3", 16.2));
//			//save Comments to repository
//			commentRepository.save(new Comment("Someone needs to mix this", 2));
//			commentRepository.save(new Comment("Really like this part", 2));
//			commentRepository.save(new Comment("How did you do this part?", 3));
//			commentRepository.save(new Comment("Is Everyone cool to post?", 0));
//		return (args) -> {
//			//save songStates
//			songStateRepository.save(new SongState("Jack,Jill", audioFileRepository.findAudioFileByFilename("banana.mp3"),commentRepository.findCommentsByBarLocation(2), 0));
//			songStateRepository.save(new SongState("Mark", audioFileRepository.findAudioFileByFilename("smile.mp3"),commentRepository.findCommentsByBarLocation(10), 1));
//
//			//fetch all SongStates
//			log.info("Song States found with findall():");
//			log.info("---------------------------------");
//			for(SongState songState : songStateRepository.findAll()){
//				log.info(songState.toString());
//			}
//			log.info("");
//
//			//fetch SongState by ID
//			log.info("Song States found with findById(1L):");
//			log.info("----------------------------------");
//			SongState songState = songStateRepository.findById(1L);
//			log.info(songState.toString());
//			log.info("");
//
//			//fetch SongStates by user
//			log.info("Song States found with findByUsers(Mark):");
//			log.info("-----------------------------------------");
//			for(SongState songState1 : songStateRepository.findByUsers("Mark")){
//				log.info(songState1.toString());
//			}
//			log.info("");
//
//		};
//	}
}
