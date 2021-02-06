package br.edu.ifmt.controledeacesso.models.entities

import br.edu.ifmt.controledeacesso.config.NoArg
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Entidade que representa um Usuário
 *
 * @project cae-api
 * @author daohn on 25/01/2021
 */
@Entity
@NoArg
data class Usuario (
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,
  var login: String,
  var nome: String,
  var senha: String,
)