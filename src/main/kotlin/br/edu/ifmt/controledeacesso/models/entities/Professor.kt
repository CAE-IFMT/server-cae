package br.edu.ifmt.controledeacesso.models.entities

import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Entidade que representa um Professor
 *
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Entity
data class Professor(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private val id: Long,
  @NotBlank(message = "O nome é necessário")
  private val nome: String,
  @Email @NotBlank(message = "O email é necessário")
  private val email: String,
  @OneToMany(mappedBy = "professor")
  private val visitantes: List<Visitante>,
)