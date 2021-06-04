package com.example.notesbackend;

import java.time.LocalDateTime;
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

    private final NoteRepository noteRepository;
    private final ModelMapper modelMapper = new ModelMapper();
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
        if (optionalNote.isPresent()) {
            return modelMapper.map(optionalNote.get(), NoteDTO.class);
        } else {
            log.warn("Note with id: " + id + " not found");
            throw new NoteNotFoundException();
        }
    }

    public void createNewNote(NoteCreateRequestDTO noteCreateRequestDTO) {
        validateData(noteCreateRequestDTO);

        noteRepository.save(new Note(noteCreateRequestDTO.getTitle(), noteCreateRequestDTO.getContent(),
                                     LocalDateTime.now(), LocalDateTime.now()));
        log.info("New note added to repository");
    }

    private void validateData(NoteCreateRequestDTO noteCreateRequestDTO) {
        if (noteCreateRequestDTO.getTitle() == null || noteCreateRequestDTO.getContent() == null) {
            log.warn("Note values can't be null");
            throw new EmptyNoteException();
        } else if (noteCreateRequestDTO.getTitle().isEmpty() || noteCreateRequestDTO.getContent().isEmpty()) {
            log.warn("Note values can't be empty");
            throw new EmptyNoteException();
        }
    }

}
