package br.edu.ifmt.controledeacesso.domain.services
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitanteDto
import br.edu.ifmt.controledeacesso.domain.entities.Visitante
import br.edu.ifmt.controledeacesso.domain.repositories.VisitanteRepository
import br.edu.ifmt.controledeacesso.exceptions.ObjectNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class VisitanteService(
    private val repository: VisitanteRepository,
    private val modelMapper: ModelMapper,
) {
  /**
   * Consulta todos os visitantes
   */
  fun findAll(): List<VisitanteDto> {
    return repository.findAll().map(this::buildDTO)
  }

  private fun buildDTO(visitante: Visitante): VisitanteDto {
    return modelMapper.map(visitante, VisitanteDto::class.java)
  }

  /**
   * Consulta visitante por id
   *
   * @param id Identificador do visitante
   */
  fun findById(id: Long): VisitanteDto = repository
      .findById(id)
      .map { buildDTO(it) }
      .orElseThrow { ObjectNotFoundException("Visitante não encontrado") }

}