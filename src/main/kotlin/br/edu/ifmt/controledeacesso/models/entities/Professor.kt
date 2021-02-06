package br.edu.ifmt.controledeacesso.models.entities

import br.edu.ifmt.controledeacesso.config.NoArg
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

/**
 * Entidade que representa um Professor
 *
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@Entity
@NoArg
data class Professor(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,
  @NotBlank(message = "O nome é necessário")
  var nome: String,
  @Email @NotBlank(message = "O email é necessário")
  var email: String,
) {

  @OneToMany(mappedBy = "professor")
  var visitas: MutableList<Visita> = mutableListOf()

  fun adicionaVisita(visita: Visita) {
    visitas.add(visita)
    if (visita.professor?.equals(this) == true) return
    visita.professor = this
  }

  fun adicionaVisitas(visitas: List<Visita>) {
    visitas.forEach {
      adicionaVisita(it)
    }
  }
}