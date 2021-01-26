package br.edu.ifmt.controledeacesso.repositories

import br.edu.ifmt.controledeacesso.models.entities.VisitaOcorrida
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @project cae-api
 * @author daohn on 26/01/2021
 */
interface VisitaOcorridaRepository : JpaRepository<VisitaOcorrida, Long> {
}