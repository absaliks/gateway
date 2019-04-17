package absaliks.gateway.notes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotebookRepository : JpaRepository<Notebook, Int>
