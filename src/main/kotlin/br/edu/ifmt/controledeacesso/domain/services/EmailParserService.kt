package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.api.controllers.dto.ProfessorDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaSaveDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitanteDto
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
  fun parseBody(mensagem: String): MutableMap<String, String> {
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
    // fixme: Implementar parser para email do professor
    val split = Pattern.compile("[<>]")
      .splitAsStream(from)
      .collect(Collectors.toList())
    val email = split[1]
    if (!email.contains("@"))
      throw IllegalStateException("Não será possível enviar notificação por email $email")
    return email
  }

  fun buildDTO(body: String, from: String): VisitaSaveDto {
    val properties = parseBody(body)

    val visitante = VisitanteDto(
      null,
      properties["visitante"]!!,
      properties["email_visitante"]!!,
      properties["cpf"]!!
    )
    val professor = ProfessorDto(null, properties["professor"]!!, from)

    return VisitaSaveDto(
      properties["data"]!!,
      properties["motivo"]!!,
      professor,
      visitante
    )
  }

}