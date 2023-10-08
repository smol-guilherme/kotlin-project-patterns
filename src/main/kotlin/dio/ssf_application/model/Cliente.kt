package dio.ssf_application.model

import jakarta.persistence.*

@Entity
class Cliente(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long,
  var nome: String,
  @ManyToOne(fetch = FetchType.LAZY)
  var endereco: Endereco,
) {}