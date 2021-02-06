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
//  @ManyToMany(fetch = FetchType.LAZY)
//  @JoinTable(
//    name="visitante_professor",
//    joinColumns = [JoinColumn(name="visitante_id")],
//    inverseJoinColumns = [JoinColumn(name = "professor_id")])
  @ManyToOne
  private val professor: Professor,
  @ManyToOne
  private val visitante: Visitante,
)
