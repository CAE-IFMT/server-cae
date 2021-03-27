package br.edu.ifmt.controledeacesso.api.controllers.dto

import br.edu.ifmt.controledeacesso.config.NoArg
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@NoArg
data class UsuarioDto(
  var login: String,
  var nome: String,
  var token: String?,
) {
  fun toJson(): String = ObjectMapper().writeValueAsString(this)
}
