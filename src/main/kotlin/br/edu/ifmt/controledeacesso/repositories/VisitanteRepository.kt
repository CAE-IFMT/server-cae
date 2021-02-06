package br.edu.ifmt.controledeacesso.repositories

import br.edu.ifmt.controledeacesso.models.entities.Visitante
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Repository
interface VisitanteRepository : JpaRepository<Visitante, Long>