package absaliks;

import absaliks.model.Note;
import absaliks.model.User;
import absaliks.repository.NoteRepository;
import absaliks.repository.UserRepository;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner init(UserRepository userRepository, NoteRepository noteRepository) {
    return evt -> {
      val user = new User();
      user.login = "absaliks";
      user.password = "123456";
      val managedUser = userRepository.save(user);

      val note = new Note();
      note.userId = managedUser.id;
      note.title = "Finish Gateway Project";
      note.text = "It's time to finish it already";
      noteRepository.save(note);
    };
  }
}
