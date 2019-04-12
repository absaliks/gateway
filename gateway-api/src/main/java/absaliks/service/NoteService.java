package absaliks.service;

import absaliks.model.Note;
import absaliks.repository.NoteRepository;
import java.util.List;
import lombok.NonNull;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  private final NoteRepository noteRepository;

  @Autowired
  public NoteService(NoteRepository noteRepository) {
    this.noteRepository = noteRepository;
  }

  public List<Note> getNotes() {
    return noteRepository.findAll();
  }

  public Note add(Note note) {
    Validate.isTrue(note.id == 0);
    return noteRepository.save(note);
  }

  public void remove(@NonNull String name) {
    noteRepository.deleteByName(name);
  }
}
