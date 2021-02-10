package br.edu.ifmt.controledeacesso.services

import br.edu.ifmt.controledeacesso.models.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitanteDTO
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
    val visitaSave = VisitaSaveDTO(
      "11/02/2020 15:50",
      "motivo aqui",
      false,
      ProfessorDTO(null, "nome professor", "email professor"),
      VisitanteDTO(null, "nome visitante", "email visitante", "cpf")
    )
    val visita = service.save(visitaSave)

    assertEquals(visitaSave.professor.nome, visita.professor.nome)
    assertEquals(visitaSave.visitante.nome, visita.visitante.nome)
    assertEquals(visitaSave.motivo, visita.motivo)
  }

}