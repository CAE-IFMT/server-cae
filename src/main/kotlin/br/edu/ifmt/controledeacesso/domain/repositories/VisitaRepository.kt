package br.edu.ifmt.controledeacesso.domain.repositories

import br.edu.ifmt.controledeacesso.domain.entities.Visita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @project cae-api
 * @author daohn on 26/01/2021
 */
@Repository
interface VisitaRepository : JpaRepository<Visita, Long> {
}