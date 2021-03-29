package br.edu.ifmt.controledeacesso.api.controllers.dto

import br.edu.ifmt.controledeacesso.config.NoArg
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Representa uma abstração pública da entidade Visita <br>
 * @see br.edu.ifmt.controledeacesso.domain.entities.Visita
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@NoArg
data class VisitaDto(
  var id: Long,
  var data: String,
  var motivo: String,
  var ocorrido: Boolean,
  var professor: ProfessorDto,
  var visitante: VisitanteDto,
) {
  fun toJson(): String = ObjectMapper().writeValueAsString(this)
}