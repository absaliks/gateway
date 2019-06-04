package absaliks.gateway.notes

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notes")
class NoteController(private val service: NoteService) {

    @GetMapping
    fun getNotes() = service.getNotes()

    @PostMapping
    fun addNote(@RequestBody note: Note) {
        service.addNote(note)
    }
}
