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
    private final ModificationRepository modificationRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private static final Logger LOG = LoggerFactory.getLogger(NotesBackendApplication.class);

    @Autowired
    public NoteService(NoteRepository noteRepository, ModificationRepository modificationRepository) {
        this.noteRepository = noteRepository;
        this.modificationRepository = modificationRepository;
    }

    public List<NoteDTO> getNotes() {
        List<NoteDTO> notes = new ArrayList<>();

        for (Note note : noteRepository.findAll()) {
            notes.add(modelMapper.map(note, NoteDTO.class));
        }
        return notes;
    }

    public NoteDTO getNote(Long id) {
        return modelMapper.map(getNoteById(id), NoteDTO.class);
    }

    public void createNewNote(NoteCreateRequestDTO noteCreateRequestDTO) {
        validateData(noteCreateRequestDTO);

        noteRepository.save(new Note(noteCreateRequestDTO.getTitle(), noteCreateRequestDTO.getContent(),
                                     LocalDateTime.now(), LocalDateTime.now()));
        LOG.info("New note added to repository");
    }

    public void updateNote(Long id, NoteCreateRequestDTO noteCreateRequestDTO) {
        Note note = getNoteById(id);
        validateData(noteCreateRequestDTO);

        Modification modification = new Modification(note.getTitle(), note.getContent(), note.getCreated(),
                                                     note.getModified(), note.getId());

        modificationRepository.save(modification);
        LOG.info("Modification saved in repository");

        note.setTitle(noteCreateRequestDTO.getTitle());
        note.setContent(noteCreateRequestDTO.getContent());
        note.setModified(LocalDateTime.now());

        noteRepository.save(note);
        LOG.info("Updated note with id: " + id);
    }

    public void deleteNote(Long id) {   //TODO change to do not delete objects from database
        if (noteRepository.existsById(id)) noteRepository.deleteById(id);
        else throw new NoteNotFoundException();
    }

    public List<Modification> getNoteHistory(Long id) {
        return modificationRepository.getAllByNoteId(id);
    }

    private void validateData(NoteCreateRequestDTO noteCreateRequestDTO) {
        if (noteCreateRequestDTO.getTitle() == null || noteCreateRequestDTO.getContent() == null) {
            LOG.warn("Note values can't be null");
            throw new EmptyNoteException();
        } else if (noteCreateRequestDTO.getTitle().isEmpty() || noteCreateRequestDTO.getContent().isEmpty()) {
            LOG.warn("Note values can't be empty");
            throw new EmptyNoteException();
        }
    }

    private Note getNoteById(Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);
        if (optionalNote.isPresent()) {
            return optionalNote.get();
        } else {
            LOG.warn("Note with id: " + id + " not found");
            throw new NoteNotFoundException();
        }
    }

}
