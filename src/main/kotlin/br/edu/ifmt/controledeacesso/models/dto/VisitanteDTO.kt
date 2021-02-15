package br.edu.ifmt.controledeacesso.models.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Representa uma abstração pública da entidade Visitante <br>
 * @see br.edu.ifmt.controledeacesso.models.entities.Visitante
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@NoArg
data class VisitanteDTO(
  var id: Long? = null,
  var nome: String,
  var email: String,
  var cpf: String,
)