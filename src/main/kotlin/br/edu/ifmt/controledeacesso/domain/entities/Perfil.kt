package br.edu.ifmt.controledeacesso.domain.entities

import br.edu.ifmt.controledeacesso.config.NoArg
import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@NoArg
@Entity
data class Perfil(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long,
  val nome: String,
) : GrantedAuthority {
  override fun getAuthority(): String = this.nome
}
