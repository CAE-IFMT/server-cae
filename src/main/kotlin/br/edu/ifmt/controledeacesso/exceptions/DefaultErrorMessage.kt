package br.edu.ifmt.controledeacesso.exceptions

import com.fasterxml.jackson.databind.ObjectMapper

data class DefaultErrorMessage(
  val currentTimeMillis: Long = System.currentTimeMillis(),
  val status: Int,
  val error: String,
  val message: String,
  val path: String?
) {
  fun toJson(): String = ObjectMapper().writeValueAsString(this)
}
