package com.example.notesbackend.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.notesbackend.DTOs.NoteCreateRequestDTO;
import com.example.notesbackend.DTOs.NoteDTO;
import com.example.notesbackend.Modification;
import com.example.notesbackend.NoteService;

@RestController
@RequestMapping(path = "notes")
public class NoteRestController {

    private final NoteService noteService;

    @GetMapping("/all")
    public List<NoteDTO> getAllNotes() {
        return noteService.getNotes("created", "desc");
    }

    @Autowired
    public NoteRestController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public NoteDTO getNoteById(@PathVariable Long id) {
        return noteService.getNote(id);
    }

    @PostMapping("/add")
    public void createNewNote(@RequestBody NoteCreateRequestDTO noteCreateRequestDTO) {
        noteService.createNewNote(noteCreateRequestDTO);
    }

    @PutMapping("/{id}")
    public void updateNote(@PathVariable Long id, @RequestBody NoteCreateRequestDTO noteCreateRequestDTO) {
        noteService.updateNote(id, noteCreateRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

    @GetMapping("/history/{id}")
    public List<Modification> getNoteHistory(@PathVariable Long id) {
        return noteService.getNoteHistory(id);
    }

}
