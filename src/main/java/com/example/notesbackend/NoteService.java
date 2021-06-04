package com.example.notesbackend;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private NoteRepository noteRepository;
    private ModelMapper modelMapper = new ModelMapper();
    private static final Logger log = LoggerFactory.getLogger(NotesBackendApplication.class);

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteDTO> getNotes() {
        List<NoteDTO> notes = new ArrayList<>();

        for (Note note : noteRepository.findAll()) {
            notes.add(modelMapper.map(note, NoteDTO.class));
        }

        return notes;
    }

    public NoteDTO getNoteById(Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent())
            return modelMapper.map(optionalNote.get(), NoteDTO.class);
        else
            throw new NoteNotFoundException();
    }

}
