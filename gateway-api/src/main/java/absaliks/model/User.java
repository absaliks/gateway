package absaliks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  @GeneratedValue
  public int id;
  public String firstName;
  public String lastName;
  public String login;
  public String password;
}
