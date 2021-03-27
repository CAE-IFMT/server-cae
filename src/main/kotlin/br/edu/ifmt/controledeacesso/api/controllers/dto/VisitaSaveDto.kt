package br.edu.ifmt.controledeacesso.api.controllers.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Created by daohn on 09/02/2021
 * @author daohn
 * @since 09/02/2021
 * @version 1.0.0
 */
@NoArg
data class VisitaSaveDto(
  var data: String,
  var motivo: String,
  var professor: ProfessorDto,
  var visitante: VisitanteDto,
  var ocorrido: Boolean = false,
)