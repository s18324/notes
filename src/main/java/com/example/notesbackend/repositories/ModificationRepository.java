package com.example.notesbackend.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.notesbackend.Modification;

public interface ModificationRepository extends CrudRepository<Modification, Long> {
    List<Modification> getAllByNoteId(Long id);
}
