package br.edu.ifmt.controledeacesso.services

import br.edu.ifmt.controledeacesso.models.dto.VisitaDTO
import br.edu.ifmt.controledeacesso.models.entities.Visita
import br.edu.ifmt.controledeacesso.repositories.VisitaRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

/**
 * Serviço responsável pela gestão das regras de negócio relacionado a entidade Visita
 * @see Visita
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Service
class VisitaService(
  private val repository: VisitaRepository,
  private val modelMapper: ModelMapper,
) {

  fun findAll(): List<VisitaDTO> {
    return repository.findAll().map(this::buildDTO)
  }

  private fun buildDTO(visita: Visita): VisitaDTO {
    return modelMapper.map(visita, VisitaDTO::class.java)
  }

}