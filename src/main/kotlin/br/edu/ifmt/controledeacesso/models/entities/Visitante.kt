package br.edu.ifmt.controledeacesso.models.entities

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Entidade que representa um visitante
 *
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Entity
data class Visitante(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private val id: Long,
  @NotBlank(message = "O nome é necessário")
  private val nome: String,
  @Email @NotBlank(message = "O email é necessário")
  private val email: String,
  @ManyToOne
  private val professor: Professor,

  @OneToMany
  private val visitaOcorrida: List<VisitaOcorrida>,

  private val cpf: String,
  private val data: LocalDateTime,
  private val motivo: String,
)