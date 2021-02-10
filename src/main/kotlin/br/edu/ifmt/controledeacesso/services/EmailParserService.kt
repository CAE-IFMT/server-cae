package br.edu.ifmt.controledeacesso.services

import org.springframework.stereotype.Service
import java.time.LocalDateTime

/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Service
class EmailParserService {

  @Throws(Exception::class)
  fun parseDate(properties: MutableMap<String, String>): String {
    val data = properties["data"]!!
    val hora = properties["hora"]!!

    var dataFormatada = data
    dataFormatada += " "
    dataFormatada += hora
    println(dataFormatada)
    return dataFormatada

//    val dataInt = data.split("/").map { it.toInt() }
//    val horaInt = hora.split(":").map { it.toInt() }
//
//    return toLocalDate(dataInt[2], dataInt[1], dataInt[0], horaInt[0], horaInt[1])
  }

  @Throws(Exception::class)
  private fun toLocalDate(ano: Int, mes: Int, dia: Int, hora: Int, minuto: Int) =
    LocalDateTime.of(ano, mes, dia, hora, minuto)

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
      map[it[0]] = it[1]
    }
    return map
  }
}