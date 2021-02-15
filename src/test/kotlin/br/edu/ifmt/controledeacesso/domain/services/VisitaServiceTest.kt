package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.domain.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.domain.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.domain.dto.VisitanteDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.format.DateTimeFormatter

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
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val visitaSave = VisitaSaveDTO(
      "11/02/2020",
      "motivo aqui",
      ProfessorDTO(null, "nome professor", "email professor"),
      VisitanteDTO(null, "nome visitante", "email visitante", "cpf")
    )
    val visita = service.save(visitaSave)

    println(visitaSave)
    println(visita)

    assertEquals(visitaSave.professor.nome, visita.professor.nome)
    assertEquals(visitaSave.data, formatter.format(visita.data))
    assertEquals(visitaSave.visitante.nome, visita.visitante.nome)
    assertEquals(visitaSave.motivo, visita.motivo)
  }

}