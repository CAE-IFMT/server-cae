package br.edu.ifmt.controledeacesso.models.entities

import java.time.LocalDateTime
import javax.persistence.*

/**
 * Entidade que representa uma visita ocorrida
 *
 * @project cae-api
 * @author daohn on 25/01/2021
 */
@Entity
data class Visita(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private val id: Long,
  private val data: LocalDateTime,
  private val motivo: String,
  private val ocorrido: Boolean,
  @ManyToOne
  private val professor: Professor,
  @ManyToOne
  private val visitante: Visitante,
)
