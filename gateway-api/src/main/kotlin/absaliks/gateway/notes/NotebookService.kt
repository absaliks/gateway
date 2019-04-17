package absaliks.gateway.notes

import org.apache.commons.lang3.Validate
import org.springframework.stereotype.Service

@Service
class NotebookService(private val repository: NotebookRepository) {

    fun getNotebooks(): List<Notebook> = repository.findAll()

    fun addNotebook(notebook: Notebook): Notebook {
        Validate.isTrue(notebook.id == 0)
        return repository.save(notebook)
    }

    fun removeNotebook(id: Int) = repository.deleteById(id)
}
