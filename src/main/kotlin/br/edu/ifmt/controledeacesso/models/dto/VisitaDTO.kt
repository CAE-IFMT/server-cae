package br.edu.ifmt.controledeacesso.models.dto

import br.edu.ifmt.controledeacesso.config.NoArg
import java.time.LocalDateTime

/**
 * Representa uma abstração pública da entidade Visita <br>
 * @see br.edu.ifmt.controledeacesso.models.entities.Visita
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@NoArg
data class VisitaDTO(
  var id: Long,
  var data: LocalDateTime?,
  var motivo: String,
  var ocorrido: Boolean,
  var professor: ProfessorDTO,
  var visitante: VisitanteDTO,
)