package absaliks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Note {

  @Id
  @GeneratedValue
  public int id;
  public int userId;
  public String title;
  public String text;
}
