package br.edu.ifmt.controledeacesso.repositories

import br.edu.ifmt.controledeacesso.models.entities.Usuario
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @project cae-api
 * @author daohn on 26/01/2021
 */
interface UsuarioRepository : JpaRepository<Usuario, Long> {
}