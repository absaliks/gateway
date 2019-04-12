package absaliks.controller;

import absaliks.model.User;
import absaliks.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private final UserService service;

  @GetMapping("/{login}")
  public User getUser(@PathVariable String login) {
    return service.getByUsername(login);
  }
}
