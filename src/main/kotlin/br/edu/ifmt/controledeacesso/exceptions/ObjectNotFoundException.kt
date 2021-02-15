package br.edu.ifmt.controledeacesso.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by daohn on 15/02/2021
 * @author daohn
 * @since 15/02/2021
 * @version 1.0.0
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
class ObjectNotFoundException(message: String?, cause: Throwable?) :
  RuntimeException(message, cause) {
  constructor(message: String?) : this(message, null)
}