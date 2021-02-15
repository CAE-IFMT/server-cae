package br.edu.ifmt.controledeacesso.services

import br.edu.ifmt.controledeacesso.models.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitanteDTO
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import java.util.stream.Collectors

/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Service
class EmailParserService {
  @Throws(Exception::class)
  private fun parseBody(mensagem: String): MutableMap<String, String> {
    val values = mensagem
      .split("\n")
      .filter { str -> str.isNotBlank() || str.isNotEmpty() }
      .map { str -> str.split("=") }
    return this.createMap(values)
  }

  @Throws(Exception::class)
  private fun createMap(values: List<List<String>>): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()

    values.forEach {
      map[it[0]] = it[1].trim()
    }

    this.sanitizeEmail(map)

    return map
  }

  private fun sanitizeEmail(properties: MutableMap<String, String>) {
    val email = properties["email_visitante"]!!
    val split = email.split(" ")
    // verifica se a primeira parte da String contém um email
    // gabrielhondacba@gmail.com <carlosheng6@gmail.com>
    // caso não tenha utiliza o email entre '<' e '>'
    if (split[0].contains("@")) {
      properties["email_visitante"] = split[0]
      return
    } else {
      properties["email_visitante"]?.let { this.extractEmail(it) }
      return
    }
  }

  fun extractEmail(from: String): String {
    val split = Pattern.compile("[<>]")
      .splitAsStream(from)
      .collect(Collectors.toList())
    val email = split[1]
    if (!email.contains("@"))
      throw IllegalStateException("Não será possível enviar notificação por email $email")
    return email
  }

  fun buildDTO(body: String, from: String): VisitaSaveDTO {
    val properties = parseBody(body)

    val visitante = VisitanteDTO(
      null,
      properties["visitante"]!!,
      properties["email_visitante"]!!,
      properties["cpf"]!!
    )
    val professor = ProfessorDTO(null, properties["professor"]!!, from)

    return VisitaSaveDTO(
      properties["data"]!!,
      properties["motivo"]!!,
      professor,
      visitante
    )
  }

}