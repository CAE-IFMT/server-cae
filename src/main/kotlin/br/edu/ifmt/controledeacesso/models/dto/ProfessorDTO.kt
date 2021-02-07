package br.edu.ifmt.controledeacesso.models.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Representa uma abstração pública da entidade Professor <br>
 * @see br.edu.ifmt.controledeacesso.models.entities.Professor
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@NoArg
data class ProfessorDTO(
  var id: Long?,
  var nome: String,
  var email: String,
)