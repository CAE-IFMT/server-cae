package br.edu.ifmt.controledeacesso.domain.repositories

import br.edu.ifmt.controledeacesso.domain.entities.Professor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Repository
interface ProfessorRepository : JpaRepository<Professor, Long> {
  fun findByEmailAndNome(nome: String, email: String): Optional<Professor>
}