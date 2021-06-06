package com.example.notesbackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.notesbackend.NoteService;

@Controller
@RequestMapping(path = "notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getNotes(Model model) {
        return getNotesSorted(model, "created", "desc");
    }

    @GetMapping("/list")
    public String getNotesSorted(Model model, @RequestParam("sortColumn") String sortColumn,
                                 @RequestParam("sortDirection") String sortDirection) {

        model.addAttribute("notes", noteService.getNotes(sortColumn, sortDirection));
        model.addAttribute("sortColumn", sortColumn);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return "notes";
    }

}
