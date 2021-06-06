package com.example.notesbackend;

import java.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.notesbackend.DTOs.NoteCreateRequestDTO;
import com.example.notesbackend.exceptions.BadRequestException;
import com.example.notesbackend.exceptions.NoteNotFoundException;
import com.example.notesbackend.repositories.ModificationRepository;
import com.example.notesbackend.repositories.NoteRepository;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private ModificationRepository modificationRepository;

    private NoteService noteService;
    private Note note;

    @Before
    public void setUp() {
        noteService = new NoteService(noteRepository, modificationRepository);
        note = new Note("title", "content", LocalDateTime.now(), LocalDateTime.now());
        noteRepository.save(note);
        modificationRepository.save(new Modification(note.getTitle(), note.getContent(), note.getCreated(),
                                                     note.getModified(), note.getId()));
    }

    @Test
    public void getNotes_ShouldReturnNotesList() {
        assertEquals(noteService.getNotes("title", "asc").get(0).getId(), note.getId());
    }

    @Test
    public void getNote_ShouldReturnNote() {
        assertEquals(noteService.getNote(note.getId()).getId(), note.getId());
    }

    @Test(expected = NoteNotFoundException.class)
    public void getNote_ShouldThrowNotFoundException() {
        noteService.getNote(20L);
    }

    @Test
    public void updateNote_Successful() {
        NoteCreateRequestDTO noteCreateRequestDTO = new NoteCreateRequestDTO();
        noteCreateRequestDTO.setTitle("new title");
        noteCreateRequestDTO.setContent("new content");

        noteService.updateNote(note.getId(), noteCreateRequestDTO);
        assertEquals(noteService.getNote(note.getId()).getTitle(), noteCreateRequestDTO.getTitle());
        assertEquals(noteService.getNote(note.getId()).getContent(), noteCreateRequestDTO.getContent());
    }

    @Test(expected = NoteNotFoundException.class)
    public void updateNote_ShouldThrowNotFoundException() {
        NoteCreateRequestDTO noteCreateRequestDTO = new NoteCreateRequestDTO();
        noteService.updateNote(20L, noteCreateRequestDTO);
    }

    @Test(expected = BadRequestException.class)
    public void updateNote_ShouldThrowForbiddenException() {
        NoteCreateRequestDTO noteCreateRequestDTO = new NoteCreateRequestDTO();
        noteService.updateNote(note.getId(), noteCreateRequestDTO);
    }

    @Test(expected = NoteNotFoundException.class)
    public void deleteNote_Successful() {
        noteService.deleteNote(note.getId());
        noteService.getNote(note.getId());
    }

    @Test(expected = NoteNotFoundException.class)
    public void deleteNote_ShouldThrowNotFoundException() {
        noteService.deleteNote(20L);
    }

    @Test
    public void getNoteHistory_ShouldReturnModificationsList() {
        assertFalse(noteService.getNoteHistory(note.getId()).isEmpty());
        assertEquals(noteService.getNoteHistory(note.getId()).get(0).getNoteId(), note.getId());
    }

    @Test
    public void getNoteHistory_ShouldReturnEmptyList() {
        assertTrue(noteService.getNoteHistory(20L).isEmpty());
    }

}
