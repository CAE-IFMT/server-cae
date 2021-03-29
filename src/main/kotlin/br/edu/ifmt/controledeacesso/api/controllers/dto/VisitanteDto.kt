package br.edu.ifmt.controledeacesso.api.controllers.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Representa uma abstração pública da entidade Visitante <br>
 * @see br.edu.ifmt.controledeacesso.domain.entities.Visita
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@NoArg
data class VisitanteDto(
  var id: Long? = null,
  var nome: String,
  var email: String,
  var cpf: String,
)