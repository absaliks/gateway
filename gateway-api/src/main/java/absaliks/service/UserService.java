package absaliks.service;

import absaliks.model.User;
import absaliks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public User getByUsername(String username) {
    return repository.findByUsername(username);
  }

}
