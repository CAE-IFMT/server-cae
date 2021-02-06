package br.edu.ifmt.controledeacesso.config

import br.edu.ifmt.controledeacesso.models.entities.Professor
import br.edu.ifmt.controledeacesso.models.entities.Usuario
import br.edu.ifmt.controledeacesso.models.entities.Visita
import br.edu.ifmt.controledeacesso.models.entities.Visitante
import br.edu.ifmt.controledeacesso.repositories.ProfessorRepository
import br.edu.ifmt.controledeacesso.repositories.UsuarioRepository
import br.edu.ifmt.controledeacesso.repositories.VisitaRepository
import br.edu.ifmt.controledeacesso.repositories.VisitanteRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Configuration
class DatabaseConfig(
  private val professorRepository: ProfessorRepository,
  private val usuarioRepository: UsuarioRepository,
  private val visitanteRepository: VisitanteRepository,
  private val visitaRepository: VisitaRepository,
) : CommandLineRunner {

  override fun run(vararg args: String?) {

    val p1 = Professor(null, "Gabriel", "gabriel@gmail.com")
    val p2 = Professor(null, "Ana", "ana@gmail.com")
    val p3 = Professor(null, "Carlos", "carlos@gmail.com")

    val v1 = Visitante(null, "visitante 1", "visitanteUm@gmail.com", "000.000.000-00")
    val v2 = Visitante(null, "visitante 2", "visitanteDois@gmail.com", "000.111.000-00")
    val v3 = Visitante(null, "visitante 3", "visitanteTres@gmail.com", "000.222.000-00")

    professorRepository.saveAll(listOf(p1, p2, p3))
    visitanteRepository.saveAll(listOf(v1, v2, v3))

    val visita1 = Visita(
      null, LocalDateTime.now().plusDays(10),
      "Buscar livro", false, p1, v1
    )
    val visita2 = Visita(
      null, LocalDateTime.now().minusDays(5).minusHours(3),
      "Atualizar documentos", true, p2, v2
    )
    val visita3 = Visita(
      null, LocalDateTime.now().minusDays(1).minusHours(10), "Devolver livro",
      false, p3, v3
    )
    val visita4 = Visita(
      null, LocalDateTime.now().minusHours(10), "Devolver livro",
      false, p1, v2
    )
    val visita5 = Visita(
      null, LocalDateTime.now().plusHours(3), "Devolver livro",
      false, p2, v3
    )
    val visita6 = Visita(
      null, LocalDateTime.now().plusDays(1), "Devolver livro",
      false, p2, v1
    )

    visitaRepository.saveAll(listOf(visita1, visita2, visita3, visita4, visita5, visita6))

    val u1 = Usuario(null, "admin", "admin", "admin")
    val u2 = Usuario(null, "recepcionista", "recepcionista", "recepcionista")
    val u3 = Usuario(null, "usuario", "usuario", "usuario")

    usuarioRepository.saveAll(listOf(u1, u2, u3))
  }

}