package dio.ssf_application.model

import jakarta.persistence.*

@Entity
data class Client(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long,
  var nome: String,
  @ManyToOne(fetch = FetchType.LAZY)
  var endereco: Address,
) {}