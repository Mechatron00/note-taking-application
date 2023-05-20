package com.notetaking.repository;

import com.notetaking.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>
{

    Optional<Note> getNoteByIdAndTitle(Long id, String title);
    Optional<Note> getNoteByIdAndDateCreated(Long id, Date date);
    Optional<Note> getNoteByIdAndDateUpdated(Long id, Date date);
    Optional<Note> deleteNoteByIdAndTitle(Long id, String title);

}
