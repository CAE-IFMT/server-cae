package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.domain.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.domain.entities.Professor
import br.edu.ifmt.controledeacesso.domain.repositories.ProfessorRepository
import br.edu.ifmt.controledeacesso.exceptions.ObjectNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class ProfessorService(
    private val repository: ProfessorRepository,
    private val modelMapper: ModelMapper,
) {


  fun findAll(): List<ProfessorDTO> {
    return repository.findAll().map(this::buildDTO)
  }

  private fun buildDTO(professor: Professor): ProfessorDTO {
    return modelMapper.map(professor, ProfessorDTO::class.java)
  }

  fun findById(id: Long): ProfessorDTO = repository
      .findById(id)
      .map { buildDTO(it) }
      .orElseThrow { ObjectNotFoundException("Professor não encontrada") }
}