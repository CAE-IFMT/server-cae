package br.edu.ifmt.controledeacesso.domain.entities

import org.springframework.security.core.GrantedAuthority

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
enum class Perfil(val id: Int, val nome: String) : GrantedAuthority {
  USUARIO(1, "ROLE_USUARIO") {
    override fun getAuthority(): String = this.nome

  },
  RECEPCIONISTA(2, "ROLE_RECEPCIONISTA") {
    override fun getAuthority(): String = this.nome
  },
  ADMIN(3, "ROLE_ADMIN") {
    override fun getAuthority(): String = this.nome
  }
}
