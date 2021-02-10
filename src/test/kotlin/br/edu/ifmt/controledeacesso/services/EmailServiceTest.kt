package br.edu.ifmt.controledeacesso.services

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 * Created by daohn on 10/02/2021
 * @author daohn
 * @since 10/02/2021
 * @version 1.0.0
 */
@SpringBootTest
class EmailServiceTest {

  @Autowired
  private lateinit var service: EmailService

  @Test
  fun createVisitaTest() {

    val body = """
      professor=Gabriel José Curvo Honda
      
      visitante=Carlos Henrique
      email_visitante=carlos@email.com
      cpf=000.000.000-10
      data=20/01/2021
      hora=15:30
      motivo=descreva aqui, de maneira sucinta, o motivo da visita
      
    """.trimIndent()

    assertDoesNotThrow {
      service.createVisita("gabriel@gmail.com", "319851398319", body)
    }
  }

}