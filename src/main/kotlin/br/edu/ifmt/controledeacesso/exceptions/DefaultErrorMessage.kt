package br.edu.ifmt.controledeacesso.exceptions

data class DefaultErrorMessage(
  val currentTimeMillis: Long = System.currentTimeMillis(),
  val status: Int,
  val error: String,
  val message: String,
  val path: String?
) {

}
