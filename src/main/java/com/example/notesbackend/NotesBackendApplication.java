package com.example.notesbackend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesBackendApplication {

    private static final Logger log = LoggerFactory.getLogger(NotesBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NotesBackendApplication.class, args);
    }

}
