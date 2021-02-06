package br.edu.ifmt.controledeacesso.models.entities

import br.edu.ifmt.controledeacesso.config.NoArg
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Entidade que representa um visitante
 *
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Entity
@NoArg
data class Visitante(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,
  @NotBlank(message = "O nome é necessário")
  var nome: String,
  @Email @NotBlank(message = "O email é necessário")
  var email: String,
  @NotBlank(message = "O cpf é necessário")
  var cpf: String,
) {
  @OneToMany(mappedBy = "visitante")
  var visitas: MutableList<Visita> = mutableListOf()

  fun adicionaVisita(visita: Visita) {
    visitas.add(visita)
    if (visita.professor?.equals(this) == true) return
    visita.visitante = this
  }

  fun adicionaVisitas(visitas: List<Visita>) {
    visitas.forEach {
      adicionaVisita(it)
    }
  }
}