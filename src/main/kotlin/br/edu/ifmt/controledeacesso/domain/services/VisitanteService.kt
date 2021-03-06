package br.edu.ifmt.controledeacesso.domain.services
import br.edu.ifmt.controledeacesso.domain.dto.VisitanteDTO
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
  fun findAll(): List<VisitanteDTO> {
    return repository.findAll().map(this::buildDTO)
  }

  private fun buildDTO(visitante: Visitante): VisitanteDTO {
    return modelMapper.map(visitante, VisitanteDTO::class.java)
  }

  fun findById(id: Long): VisitanteDTO = repository
      .findById(id)
      .map { buildDTO(it) }
      .orElseThrow { ObjectNotFoundException("Visitante não encontrado") }

}