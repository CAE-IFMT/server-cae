package br.edu.ifmt.controledeacesso.api.controllers

import br.edu.ifmt.controledeacesso.domain.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.domain.services.ProfessorService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/professores")
class ProfessorController(private val service: ProfessorService) {

  @GetMapping
  @Secured("ROLE_ADMIN")
  fun findAll(): ResponseEntity<List<ProfessorDTO>> {
    val professor = service.findAll()
    return ResponseEntity.ok(professor)
  }

  @GetMapping("/{id}")
  @Secured("ROLE_ADMIN")
  fun findById(@PathVariable("id") id: Long): ResponseEntity<ProfessorDTO> {
    val professor = service.findById(id)
    return ResponseEntity.ok(professor)
  }
}