package br.edu.ifmt.controledeacesso.models.entities

import br.edu.ifmt.controledeacesso.config.NoArg
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Entidade que representa uma visita ocorrida
 *
 * @project cae-api
 * @author daohn on 25/01/2021
 */
@Entity
@NoArg
data class Visita(
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long?,
  var data: LocalDateTime,
  var motivo: String,
  var ocorrido: Boolean,
  @ManyToOne(
    cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH],
    fetch = FetchType.EAGER
  )
  @JoinColumn(name = "professor_id")
  var professor: Professor?,
  @ManyToOne(
    cascade = [CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH],
    fetch = FetchType.EAGER
  )
  @JoinColumn(name = "visitante_id")
  var visitante: Visitante?,
)