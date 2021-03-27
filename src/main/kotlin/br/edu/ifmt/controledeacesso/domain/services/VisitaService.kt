package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaSaveDto
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

  fun findAll(): List<VisitaDto> {
    return repository.findAll().map(this::buildDTO)
  }

  fun save(visitaDto: VisitaSaveDto): VisitaDto {
    val entity = parseDTO(visitaDto)
    val visita = repository.save(entity)
    return modelMapper.map(visita, VisitaDto::class.java)
  }

  fun findById(id: Long): VisitaDto = repository
    .findById(id)
    .map { buildDTO(it) }
    .orElseThrow { ObjectNotFoundException("Visita não encontrada") }


  private fun parseDTO(dto: VisitaSaveDto): Visita {
    val visita = modelMapper.map(dto, Visita::class.java)
      ?: throw IllegalStateException("Algo deu errado durante a construção da Visita")
    addProfessor(visita, dto)
    addVisitante(visita, dto)
    return visita
  }

  private fun addVisitante(visita: Visita, dto: VisitaSaveDto) {

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

  private fun addProfessor(visita: Visita, dto: VisitaSaveDto) {

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

  private fun buildDTO(visita: Visita): VisitaDto {
    return modelMapper.map(visita, VisitaDto::class.java)
  }

  fun updateVisitaOcorridaStatus(id: Long?): VisitaDto? {
    if (id == null) throw IllegalStateException("Não foi possível atualizar a visita")

    val visitaOptional = repository.findById(id)

    if (!visitaOptional.isPresent)
      throw ObjectNotFoundException("Não foi possível encontrar a visita")

    val visita = visitaOptional.get()

    visita.apply {
      this.ocorrido = true
    }

    repository.save(visita)

    return buildDTO(visita)

  }

  fun findByVisitasOcorridas(): List<VisitaDto> =
    repository
      .filterByVisitaOcorrida(true)
      .map(this::buildDTO)


  fun findByVisitasNaoOcorridas(): List<VisitaDto> =
    repository
      .filterByVisitaOcorrida(false)
      .map(this::buildDTO)


}