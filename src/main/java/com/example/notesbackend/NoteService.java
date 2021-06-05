package com.example.notesbackend;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private static final Logger LOG = LoggerFactory.getLogger(NotesBackendApplication.class);
    private final NoteRepository noteRepository;
    private final ModificationRepository modificationRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public NoteService(NoteRepository noteRepository, ModificationRepository modificationRepository) {
        this.noteRepository = noteRepository;
        this.modificationRepository = modificationRepository;
    }

    public List<NoteDTO> getNotes(String sortColumn, String sortDirection) {
        List<NoteDTO> notes = new ArrayList<>();

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortColumn).ascending() :
                Sort.by(sortColumn).descending();

        if (sortColumn.equals("title") && sortDirection.equals("asc")) {
            for (Note note : noteRepository.findAllByIsVisibleOrderByTitleAsc(true)) {
                notes.add(modelMapper.map(note, NoteDTO.class));
            }
        } else if (sortColumn.equals("title") && sortDirection.equals("desc")) {
            for (Note note : noteRepository.findAllByIsVisibleOrderByTitleDesc(true)) {
                notes.add(modelMapper.map(note, NoteDTO.class));
            }
        } else if (sortColumn.equals("created") && sortDirection.equals("asc")) {
            for (Note note : noteRepository.findAllByIsVisibleOrderByCreatedAsc(true)) {
                notes.add(modelMapper.map(note, NoteDTO.class));
            }
        } else if (sortColumn.equals("created") && sortDirection.equals("desc")) {
            for (Note note : noteRepository.findAllByIsVisibleOrderByCreatedDesc(true)) {
                notes.add(modelMapper.map(note, NoteDTO.class));
            }
        } else if (sortColumn.equals("modified") && sortDirection.equals("asc")) {
            for (Note note : noteRepository.findAllByIsVisibleOrderByModifiedAsc(true)) {
                notes.add(modelMapper.map(note, NoteDTO.class));
            }
        } else if (sortColumn.equals("modified") && sortDirection.equals("desc")) {
            for (Note note : noteRepository.findAllByIsVisibleOrderByModifiedDesc(true)) {
                notes.add(modelMapper.map(note, NoteDTO.class));
            }
        }

        /*for (Note note : noteRepository.findAll(sort)) {
            notes.add(modelMapper.map(note, NoteDTO.class));
        }*/

        return notes;
    }

    public NoteDTO getNote(Long id) {
        return modelMapper.map(getNoteById(id), NoteDTO.class);
    }

    public void createNewNote(NoteCreateRequestDTO noteCreateRequestDTO) {
        validateData(noteCreateRequestDTO);

        Note note = new Note(noteCreateRequestDTO.getTitle(), noteCreateRequestDTO.getContent(),
                             LocalDateTime.now(), LocalDateTime.now());
        noteRepository.save(note);
        LOG.info("New note added to repository");

        saveNoteVersion(note);
    }

    public void updateNote(Long id, NoteCreateRequestDTO noteCreateRequestDTO) {
        Note note = getNoteById(id);
        validateData(noteCreateRequestDTO);

        note.setTitle(noteCreateRequestDTO.getTitle());
        note.setContent(noteCreateRequestDTO.getContent());
        note.setModified(LocalDateTime.now());

        noteRepository.save(note);
        LOG.info("Updated note with id: " + id);

        saveNoteVersion(note);
    }

    public void deleteNote(Long id) {
        Note note = getNoteById(id);
        note.setVisible(false);
        noteRepository.save(note);
    }

    public List<Modification> getNoteHistory(Long id) {
        return modificationRepository.getAllByNoteId(id);
    }

    private void saveNoteVersion(Note note) {
        Modification modification = new Modification(note.getTitle(), note.getContent(), note.getCreated(),
                                                     note.getModified(), note.getId());

        modificationRepository.save(modification);
        LOG.info("Note version saved in repository");
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
        Optional<Note> optionalNote = noteRepository.findByIdAndIsVisible(id, true);
        if (optionalNote.isPresent()) {
            return optionalNote.get();
        } else {
            LOG.warn("Note with id: " + id + " not found");
            throw new NoteNotFoundException();
        }
    }

}
