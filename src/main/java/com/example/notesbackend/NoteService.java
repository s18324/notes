package com.example.notesbackend;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> getNotes() {
        List<NoteDTO> notes = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        /*Note note = new Note("This is the title", "some description", LocalDateTime.now(), LocalDateTime.now());
        NoteDTO noteDTO = modelMapper.map(note, NoteDTO.class);
        notes.add(noteDTO);*/

        for (Note note: noteRepository.findAll()) {
            notes.add(modelMapper.map(note, NoteDTO.class));
        }

        //TODO get data from database
        return notes;
    }

    /*public NoteDTO getNoteById(Long id) {

    }*/
}
