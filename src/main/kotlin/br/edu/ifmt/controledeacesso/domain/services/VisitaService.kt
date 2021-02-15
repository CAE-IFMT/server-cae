package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.domain.dto.VisitaDTO
import br.edu.ifmt.controledeacesso.domain.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.domain.entities.Professor
import br.edu.ifmt.controledeacesso.domain.entities.Visita
import br.edu.ifmt.controledeacesso.domain.entities.Visitante
import br.edu.ifmt.controledeacesso.domain.repositories.ProfessorRepository
import br.edu.ifmt.controledeacesso.domain.repositories.VisitaRepository
import br.edu.ifmt.controledeacesso.domain.repositories.VisitanteRepository
import br.edu.ifmt.controledeacesso.exceptions.ObjectNotFoundException
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
  private val visitanteRepository: VisitanteRepository,
  private val professorRepository: ProfessorRepository,
  private val modelMapper: ModelMapper,
) {

  fun findAll(): List<VisitaDTO> {
    return repository.findAll().map(this::buildDTO)
  }

  fun save(visitaDTO: VisitaSaveDTO): VisitaDTO {
    val entity = parseDTO(visitaDTO)
    val visita = repository.save(entity)
    return modelMapper.map(visita, VisitaDTO::class.java)
  }

  fun findById(id: Long): VisitaDTO = repository
    .findById(id)
    .map { buildDTO(it) }
    .orElseThrow { ObjectNotFoundException("Visita não encontrada") }


  private fun parseDTO(dto: VisitaSaveDTO): Visita {
    val visita = modelMapper.map(dto, Visita::class.java)
      ?: throw IllegalStateException("Algo deu errado durante a construção da Visita")
    addProfessor(visita, dto)
    addVisitante(visita, dto)
    return visita
  }

  private fun addVisitante(visita: Visita, dto: VisitaSaveDTO) {

    val visitanteConsulta = visitanteRepository.findByNomeAndEmailAndCpf(
      dto.visitante.nome,
      dto.visitante.email,
      dto.visitante.cpf
    )

    if (visitanteConsulta.isPresent) {
      val visitante = visitanteConsulta.get()
      visitante.adicionaVisita(visita)
    } else {
      val obj = visitanteRepository.save(modelMapper.map(dto.visitante, Visitante::class.java))
      obj.adicionaVisita(visita)
    }
  }

  private fun addProfessor(visita: Visita, dto: VisitaSaveDTO) {

    val professorConsulta = professorRepository.findByEmailAndNome(
      dto.professor.nome,
      dto.professor.email
    )

    if (professorConsulta.isPresent) {
      val professor = professorConsulta.get()
      professor.adicionaVisita(visita)
    } else {
      val obj = professorRepository.save(modelMapper.map(dto.professor, Professor::class.java))
      obj.adicionaVisita(visita)
    }
  }

  private fun buildDTO(visita: Visita): VisitaDTO {
    return modelMapper.map(visita, VisitaDTO::class.java)
  }

}