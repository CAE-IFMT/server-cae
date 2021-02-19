package br.edu.ifmt.controledeacesso.domain.entities

import br.edu.ifmt.controledeacesso.config.NoArg
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

/**
 * Entidade que representa um Usuário
 *
 * @project cae-api
 * @author daohn on 25/01/2021
 */
@Entity
@NoArg
data class Usuario(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,
  var login: String,
  var nome: String,
  var senha: String,
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "usuario_perfil",
    joinColumns = [JoinColumn(name = "usuario_id", referencedColumnName = "id")],
    inverseJoinColumns = [JoinColumn(name = "perfil_id", referencedColumnName = "id")]
  )
  val perfis: List<Perfil>
) : UserDetails {
  override fun getAuthorities(): List<Perfil> = this.perfis
  override fun getPassword(): String = this.senha
  override fun getUsername(): String = this.login
  override fun isAccountNonExpired(): Boolean = true
  override fun isAccountNonLocked(): Boolean = true
  override fun isCredentialsNonExpired(): Boolean = true
  override fun isEnabled(): Boolean = true
}