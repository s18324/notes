package com.example.notesbackend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public List<NoteDTO> getNotes() {
        return noteService.getNotes();
    }

    /*@GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id){
        return noteService.getNoteById(id);
    }*/

}
