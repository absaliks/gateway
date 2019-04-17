package absaliks.gateway.notes

import org.springframework.stereotype.Service

@Service
class NoteService(private val repository: NoteRepository) {

    fun getNotes(): List<Note> = repository.findAll()

    fun addNote(note: Note): Note {
        return repository.save(note)
    }

    fun remove(id: Int) = repository.deleteById(id)
}
