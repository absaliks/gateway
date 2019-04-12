package absaliks.service;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import absaliks.repository.NoteRepository;

class NoteServiceTest {

  private static final String NOTE1 = "note1";
  private static final String NOTE2 = "note2";
  private static final String NOTE3 = "note3";

  @Mock
  private NoteRepository repository;

  private NoteService service;

  @Test
  void noteListIsEmptyByDefault() {
    assertEquals(emptyList(), service.getNotes());
  }

  /*@Test
  void ableToStoreNote_success() {
    service.add(NOTE1);
    assertEquals(asList(NOTE1), service.getNotes());
  }

  @Test
  void store3Notes_retrieve_success() {
    service.add(NOTE1);
    service.add(NOTE3);
    service.add(NOTE2);
    assertEquals(asList(NOTE1, NOTE3, NOTE2), service.getNotes());
  }

  @Test
  void removeExistingNote_success() {
    service.add(NOTE1);
    service.add(NOTE3);
    service.add(NOTE2);
    service.remove(NOTE2);
    assertEquals(asList(NOTE1, NOTE3), service.getNotes());
  }*/

  @Test
  void addNullNote_exception() {
    assertThrows(NullPointerException.class, () -> service.add(null));
  }

  @Test
  void removeNullNote_exception() {
    assertThrows(NullPointerException.class, () -> service.remove(null));
  }
}