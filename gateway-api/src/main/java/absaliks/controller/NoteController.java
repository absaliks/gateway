package absaliks.controller;

import absaliks.model.Note;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import absaliks.service.NoteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

  @Autowired
  private final NoteService service;

  @GetMapping
  public List<Note> getNotes() {
    return service.getNotes();
  }

  @PostMapping
  public Note addNote(@RequestBody Note note) {
    return service.add(note);
  }
}
