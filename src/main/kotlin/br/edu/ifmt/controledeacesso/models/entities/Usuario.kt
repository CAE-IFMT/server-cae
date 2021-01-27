package br.edu.ifmt.controledeacesso.models.entities

import javax.persistence.Entity
import javax.persistence.Id

/**
 * Entidade que representa um Usuário
 *
 * @project cae-api
 * @author daohn on 25/01/2021
 */
@Entity
data class Usuario (
  @Id val id: Long,
  val login: String,
  val nome: String,
  val senha: String,
)