package com.example.notesbackend;

import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NotesBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(NotesBackendApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(NotesBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(NoteRepository repository) {
        return (args) -> {
            // save a few customers
            repository.save(new Note("title1", "desc1", LocalDateTime.now(), LocalDateTime.now()));
            repository.save(new Note("title2", "desc2", LocalDateTime.now(), LocalDateTime.now()));

            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (Note note : repository.findAll()) {
                log.info(note.toString());
            }
        };
    }

}
