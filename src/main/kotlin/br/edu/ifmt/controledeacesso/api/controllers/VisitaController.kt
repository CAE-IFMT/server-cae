package br.edu.ifmt.controledeacesso.api.controllers

import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.VisitaSaveDto
import br.edu.ifmt.controledeacesso.domain.services.VisitaService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador responsável pela gestão dos endpoints referentes à entidade Visita
 * @see VisitaService
 * @see VisitaDto
 * @see br.edu.ifmt.controledeacesso.domain.entities.Visita
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@RestController
@RequestMapping("/visitas")
class VisitaController(private val service: VisitaService) {

  /**
   * Consulta lista de todas as visitas. Permitido para administrador e recepcionista.
   *
   * Método: GET
   *
   * URI: /visitas
   */
  @GetMapping
  @Secured("ROLE_RECEPCIONISTA", "ROLE_ADMIN")
  fun findAll(): ResponseEntity<List<VisitaDto>> {
    val visitas = service.findAll()
    return ResponseEntity.ok(visitas)
  }

  /**
   * Cria uma visita. Permitido apenas para administrador.
   *
   * Método: POST
   *
   * URI: /visitas
   */
  @PostMapping
  @Secured("ROLE_ADMIN")
  fun save(
    @RequestBody dto: VisitaSaveDto,
    uriComponentsBuilder: UriComponentsBuilder,
  ): ResponseEntity<VisitaDto> {
    val visita = service.save(dto)
    return ResponseEntity.ok(visita)
  }

  /**
   * Consulta visita por id. Permitido para administrador e recepcionista.
   *
   * Método: GET
   *
   * URI: /visitas/{id}
   */
  @GetMapping("/{id}")
  @Secured("ROLE_RECEPCIONISTA", "ROLE_ADMIN")
  fun findById(@PathVariable("id") id: Long): ResponseEntity<VisitaDto> {
    val visita = service.findById(id)
    return ResponseEntity.ok(visita)
  }

  /**
   * Atualiza o status de uma visita para ocorrida. Permitido para administrador e recepcionista.
   *
   * Método: PUT
   *
   * URI: /visitas/{id}/ocorrida
   */
  @PutMapping("/{id}/ocorrida")
  @Secured("ROLE_RECEPCIONISTA", "ROLE_ADMIN")
  fun updateVisitaOcorridaStatus(@PathVariable("id") id: Long) : ResponseEntity<VisitaDto> {
    val pessoaUpdated = service.updateVisitaOcorridaStatus(id)
    return ResponseEntity.ok(pessoaUpdated)
  }

  /**
   * Consulta lista de visitas ocorridas. Permitido para administrador e recepcionista.
   *
   * Método: GET
   *
   * URI: /visitas/ocorridas
   */
  @GetMapping("/ocorridas")
  @Secured("ROLE_RECEPCIONISTA", "ROLE_ADMIN")
  fun findByVisitasOcorridas() : ResponseEntity<List<VisitaDto>> {
    val visitas = service.findByVisitasOcorridas()
    return ResponseEntity.ok(visitas)
  }

  /**
   * Consulta lista de visitas não ocorridas. Permitido para administrador e recepcionista.
   *
   * Método: GET
   *
   * URI: /visitas/naoOcorridas
   */
  @GetMapping("/naoOcorridas")
  @Secured("ROLE_RECEPCIONISTA", "ROLE_ADMIN")
  fun findByVisitasNaoOcorridas() : ResponseEntity<List<VisitaDto>> {
    val visitas = service.findByVisitasNaoOcorridas()
    return ResponseEntity.ok(visitas)
  }
}