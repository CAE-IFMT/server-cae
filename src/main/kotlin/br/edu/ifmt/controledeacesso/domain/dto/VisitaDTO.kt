package br.edu.ifmt.controledeacesso.domain.dto

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
data class VisitaDTO(
  var id: Long,
  var data: String,
  var motivo: String,
  var ocorrido: Boolean,
  var professor: ProfessorDTO,
  var visitante: VisitanteDTO,
) {
  fun toJson(): String = ObjectMapper().writeValueAsString(this)
}