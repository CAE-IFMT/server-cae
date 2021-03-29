package br.edu.ifmt.controledeacesso.api.controllers.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Representa uma abstração pública da entidade Professor <br>
 * @see br.edu.ifmt.controledeacesso.domain.entities.Professor
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@NoArg
data class ProfessorDto(
  var id: Long? = null,
  var nome: String,
  var email: String,
)