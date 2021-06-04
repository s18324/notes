package com.example.notesbackend;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
