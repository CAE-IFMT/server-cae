package br.edu.ifmt.controledeacesso.repositories

import br.edu.ifmt.controledeacesso.models.entities.Visita
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @project cae-api
 * @author daohn on 26/01/2021
 */
interface VisitaRepository : JpaRepository<Visita, Long> {
}