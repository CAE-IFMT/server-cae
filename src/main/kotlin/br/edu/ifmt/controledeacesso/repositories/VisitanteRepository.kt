package br.edu.ifmt.controledeacesso.repositories

import br.edu.ifmt.controledeacesso.models.entities.Visitante
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @project cae-api
 * @author daohn on 24/01/2021
 */
interface VisitanteRepository : JpaRepository<Visitante, Long>