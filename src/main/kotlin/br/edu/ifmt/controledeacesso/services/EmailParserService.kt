package br.edu.ifmt.controledeacesso.services

import org.springframework.stereotype.Service

/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Service
class EmailParserService {
  @Throws(Exception::class)
  fun parseBody(mensagem: String): MutableMap<String, String> {
    val values = mensagem
      .split("\n")
      .filter { str -> str.isNotBlank() || str.isNotEmpty()}
      .map { str -> str.split("=") }
    return createMap(values)
  }

  @Throws(Exception::class)
  private fun createMap(values: List<List<String>>): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()

    values.forEach {
      map[it[0]] = it[1].trim()
    }
    return map
  }
}