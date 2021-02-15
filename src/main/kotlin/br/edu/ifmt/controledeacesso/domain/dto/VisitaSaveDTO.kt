package br.edu.ifmt.controledeacesso.domain.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Created by daohn on 09/02/2021
 * @author daohn
 * @since 09/02/2021
 * @version 1.0.0
 */
@NoArg
data class VisitaSaveDTO(
  var data: String,
  var motivo: String,
  var professor: ProfessorDTO,
  var visitante: VisitanteDTO,
  var ocorrido: Boolean = false,
)