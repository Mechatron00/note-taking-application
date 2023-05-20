package com.notetaking.controller;
import com.notetaking.model.Note;
import com.notetaking.repository.NoteRepository;
import com.notetaking.util.ExceptionHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class NoteController
{
    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/home")//testing purpose
    public String home()
    {
        return "Note-taking Application works...";
    }

    @GetMapping("/notes")//postman checked
    public ResponseEntity<?> getAllNotes()
    {
        List<Note> allNotes = noteRepository.findAll();
        return new ResponseEntity<>(allNotes,HttpStatus.OK);
    }

    @GetMapping("notes/get{title}")//postman checked
    public ResponseEntity<?> getNoteByTitle(@RequestParam("id") Long id,
                                            @RequestParam("title") String title)
    {
        Optional<Note> note = noteRepository.getNoteByIdAndTitle(id, title);
        if(note.isPresent())
        {
           Note response = note.get();
           return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
        {
            return ResponseEntity.badRequest().body(new ExceptionHandle("Sorry!!! Note not found"));
        }
    }
    @GetMapping("notes/bydatecreated{datecreated}")//postman checked
    public ResponseEntity<?> getNoteByDateCreated(@RequestParam("id") Long id,
                                                  @RequestParam("datecreated") String datecreated) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
           try
           {
               Date created = formatter.parse(datecreated);
               Optional<Note> note = noteRepository.getNoteByIdAndDateCreated(id, created);
               if(note.isPresent())
               {
                   Note response = note.get();
                   return new ResponseEntity<>(response, HttpStatus.OK);
               }
               else
               {
                   return ResponseEntity.badRequest().body("Note not found for given date");

               }
           }
           catch (ParseException exception)
           {
               return ResponseEntity.badRequest().body(new ExceptionHandle("Please correct Date input"));
           }


    }
    @GetMapping("notes/bydateupdated{lastupdated}")//postman checked
    public ResponseEntity<?> getNoteByLastUpdated(@RequestParam("id") Long id,
                                                  @RequestParam("lastupdated") String lastupdated) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
          try
          {Date updated = formatter.parse(lastupdated);
            Optional<Note> note = noteRepository.getNoteByIdAndDateUpdated(id, updated);
            if(note.isPresent())
            {
                Note response = note.get();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {
                return ResponseEntity.badRequest().body("Note not found for given date");

            }
         }
          catch (ParseException exception)
            {
                 return ResponseEntity.badRequest().body(new ExceptionHandle("Please correct Date input"));
            }

}

    @PostMapping("/notes")//postman checked
    public ResponseEntity<?> createNote(@RequestParam("title") String title,
                                        @RequestParam("description") String description,
                                        @RequestParam("startdate") String startdate)throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");

        try
        {
            Date updated = formatter.parse(startdate);
            Date created = formatter.parse(startdate);
            Note newNote = new Note(title, description, created, updated);
            Note response = noteRepository.save(newNote);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (ParseException exception)
        {
            return ResponseEntity.badRequest().body(new ExceptionHandle("Please insert correct date!"));

        }
    }

    @PutMapping("notes/update{oldtitle}")//postman checked
    public ResponseEntity<?> updateNote(@RequestParam String oldtitle,
                                        @RequestParam("id") Long id,
                                        @RequestParam("newtitle") String newtitle,
                                        @RequestParam("description") String description,
                                        @RequestParam("date") String date) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
        Optional<Note> oldNote = noteRepository.getNoteByIdAndTitle(id, oldtitle);
        if(oldNote.isPresent())
        {
            try
            {
                Date lastUpdated = formatter.parse(date);
                Note updatedNote = oldNote.get();
                updatedNote.setTitle(newtitle);
                updatedNote.setContent(description);
                updatedNote.setUpdated(lastUpdated);

                Note response = noteRepository.save(updatedNote);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            catch (ParseException exception)
            {
                return ResponseEntity.badRequest().body(new ExceptionHandle("Enter correct Date!"));

            }
        }
        else
        {
            return ResponseEntity.badRequest().body(new ExceptionHandle("Note not found"));
        }
    }

    @DeleteMapping("/notes/delete{title}")//postman checked
    public ResponseEntity<?> deleteNote(@RequestParam("id") Long id,
                                        @RequestParam String title)
    {
        Optional<Note> oldNote = noteRepository.getNoteByIdAndTitle(id, title);
        Note toDelete = oldNote.get();
        if(toDelete == null)
        {
            return ResponseEntity.badRequest().body(new ExceptionHandle("Note not found!"));
        }
        else
        {
            noteRepository.delete(toDelete);
            return new ResponseEntity<>("Deleted Successfully!", HttpStatus.OK);
        }

    }

}
