package br.edu.ifmt.controledeacesso.domain.repositories

import br.edu.ifmt.controledeacesso.domain.entities.Visitante
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Repository
interface VisitanteRepository : JpaRepository<Visitante, Long> {
  fun findByNomeAndEmailAndCpf(nome: String, email: String, cpf: String): Optional<Visitante>
}