package absaliks.repository;

import absaliks.model.Note;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

  List<Note> findByUserId(String userId);

  void deleteByName(String name);
}
