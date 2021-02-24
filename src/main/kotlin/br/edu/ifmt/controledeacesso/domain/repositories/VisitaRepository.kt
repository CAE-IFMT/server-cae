package br.edu.ifmt.controledeacesso.domain.repositories

import br.edu.ifmt.controledeacesso.domain.entities.Visita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * @project cae-api
 * @author daohn on 26/01/2021
 */
@Repository
interface VisitaRepository : JpaRepository<Visita, Long> {

  @Query(
    value = "select visita from Visita visita where visita.ocorrido = :ocorrida"
  )
  fun filterByVisitaOcorrida(@Param("ocorrida") ocorrida: Boolean) : List<Visita>
}