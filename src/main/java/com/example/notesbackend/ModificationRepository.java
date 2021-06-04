package com.example.notesbackend;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ModificationRepository extends CrudRepository<Modification, Long> {
    List<Modification> getAllByNoteId(Long id);
}
