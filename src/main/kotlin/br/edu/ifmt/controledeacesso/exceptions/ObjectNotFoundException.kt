package br.edu.ifmt.controledeacesso.exceptions

/**
 * Created by daohn on 15/02/2021
 * @author daohn
 * @since 15/02/2021
 * @version 1.0.0
 */
class ObjectNotFoundException(message: String?, cause: Throwable?) :
  RuntimeException(message, cause) {
  constructor(message: String?) : this(message, null)
}