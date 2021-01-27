package br.edu.ifmt.controledeacesso.models.entities

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Entidade que representa uma visita ocorrida
 *
 * @project cae-api
 * @author daohn on 25/01/2021
 */
@Entity
data class VisitaOcorrida(
  @Id val id: Long,
  val ocorrido: Boolean,
)
