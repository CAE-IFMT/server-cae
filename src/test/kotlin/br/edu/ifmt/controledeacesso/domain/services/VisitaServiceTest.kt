package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.api.controllers.dto.ProfessorDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaSaveDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitanteDto
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
class VisitaServiceTest {

  @Autowired
  private lateinit var service: VisitaService

  @Test
  fun saveTest() {
    val visitaSave = VisitaSaveDto(
      "11/02/2020",
      "motivo aqui",
      ProfessorDto(null, "nome professor", "email professor"),
      VisitanteDto(null, "nome visitante", "email visitante", "cpf")
    )
    val visita = service.save(visitaSave)

    println(visitaSave)
    println(visita)

    assertEquals(visitaSave.professor.nome, visita.professor.nome)
    assertEquals(visitaSave.data, visita.data)
    assertEquals(visitaSave.visitante.nome, visita.visitante.nome)
    assertEquals(visitaSave.motivo, visita.motivo)
  }

}