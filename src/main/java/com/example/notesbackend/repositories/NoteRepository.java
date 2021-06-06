package com.example.notesbackend.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.example.notesbackend.Note;

public interface NoteRepository extends CrudRepository<Note, Long> {

    List<Note> findAllByIsVisibleOrderByTitleAsc(Boolean isVisible);

    List<Note> findAllByIsVisibleOrderByTitleDesc(Boolean isVisible);

    List<Note> findAllByIsVisibleOrderByCreatedAsc(Boolean isVisible);

    List<Note> findAllByIsVisibleOrderByCreatedDesc(Boolean isVisible);

    List<Note> findAllByIsVisibleOrderByModifiedAsc(Boolean isVisible);

    List<Note> findAllByIsVisibleOrderByModifiedDesc(Boolean isVisible);

    Optional<Note> findByIdAndIsVisible(Long id, Boolean isVisible);

}
