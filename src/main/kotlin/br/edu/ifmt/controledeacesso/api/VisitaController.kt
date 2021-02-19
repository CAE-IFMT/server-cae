package br.edu.ifmt.controledeacesso.api

import br.edu.ifmt.controledeacesso.domain.dto.VisitaDTO
import br.edu.ifmt.controledeacesso.domain.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.domain.services.VisitaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * Controlador responsável pela gestão dos endpoints referentes à entidade Visita
 * @see VisitaService
 * @see VisitaDTO
 * @see br.edu.ifmt.controledeacesso.domain.entities.Visita
 *
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@RestController
@RequestMapping("/visitas")
class VisitaController(private val service: VisitaService) {

  @GetMapping
  fun findAll(): ResponseEntity<List<VisitaDTO>> {
    val visitas = service.findAll()
    return ResponseEntity.ok(visitas)
  }

  @PostMapping
  fun save(
    @RequestBody dto: VisitaSaveDTO,
    uriComponentsBuilder: UriComponentsBuilder,
  ): ResponseEntity<VisitaDTO> {
    val visita = service.save(dto)
    return ResponseEntity.ok(visita)
  }

  @GetMapping("/{id}")
  fun findById(@PathVariable("id") id: Long): ResponseEntity<VisitaDTO> {
    val visita = service.findById(id)
    return ResponseEntity.ok(visita)
  }

  @PutMapping("/ocorrido/{id}")
  fun updateVisitaOcorridaStatus(@PathVariable("id") id: Long) : ResponseEntity<VisitaDTO> {
    val pessoaUpdated = service.updateVisitaOcorridaStatus(id)
    return ResponseEntity.ok(pessoaUpdated)
  }
}