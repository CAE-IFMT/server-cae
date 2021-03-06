package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.api.controllers.dto.ProfessorDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaDto
import br.edu.ifmt.controledeacesso.domain.entities.Professor
import br.edu.ifmt.controledeacesso.domain.repositories.ProfessorRepository
import br.edu.ifmt.controledeacesso.domain.repositories.VisitaRepository
import br.edu.ifmt.controledeacesso.exceptions.ObjectNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class ProfessorService(
    private val repository: ProfessorRepository,
    private val modelMapper: ModelMapper,
    private val visitaRepository: VisitaRepository,
) {

  /**
   * Consulta todos os professores.
   */
  fun findAll(): List<ProfessorDto> {
    return repository.findAll().map(this::buildDTO)
  }

  /**
   * Converte um Professor para ProfessorDto.
   */
  private fun buildDTO(professor: Professor): ProfessorDto {
    return modelMapper.map(professor, ProfessorDto::class.java)
  }

  /**
   * Consulta um professor pelo identificador.
   */
  fun findById(id: Long): ProfessorDto = repository
      .findById(id)
      .map { buildDTO(it) }
      .orElseThrow { ObjectNotFoundException("Professor não encontrado") }

  /**
   * Consulta todas as visitas relacionadas ao professor.
   */
  fun findVisitasByProfessor(id: Long): List<VisitaDto> {
    val visitas = visitaRepository.findVisitasByProfessorId(id)
    return visitas.map { modelMapper.map(it, VisitaDto::class.java) }
  }

}