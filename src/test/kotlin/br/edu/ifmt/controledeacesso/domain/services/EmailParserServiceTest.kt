package br.edu.ifmt.controledeacesso.domain.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Created by daohn on 10/02/2021
 * @author daohn
 * @since 10/02/2021
 * @version 1.0.0
 */
@SpringBootTest
class EmailParserServiceTest{

  @Autowired
  lateinit var parserService: EmailParserService

  @Test
  fun parserBodyTest() {

    val body = """
      professor=Gabriel José Curvo Honda
      
      visitante=Carlos Henrique
      email_visitante=carlos@email.com
      cpf=000.000.000-10
      data=20/01/2021
      hora=15:30
      motivo=descreva aqui, de maneira sucinta, o motivo da visita
      
    """.trimIndent()

    val map = parserService.parseBody(body)

    assertEquals("Gabriel José Curvo Honda", map["professor"])
    assertEquals("Carlos Henrique", map["visitante"])
    assertEquals("carlos@email.com", map["email_visitante"])
    assertEquals("000.000.000-10", map["cpf"])
    assertEquals("descreva aqui, de maneira sucinta, o motivo da visita", map["motivo"])
  }
}