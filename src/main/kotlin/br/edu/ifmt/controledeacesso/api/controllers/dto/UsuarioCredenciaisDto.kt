package br.edu.ifmt.controledeacesso.api.controllers.dto

import br.edu.ifmt.controledeacesso.config.NoArg

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@NoArg
data class UsuarioCredenciaisDto(
  val login: String,
  val senha: String,
)