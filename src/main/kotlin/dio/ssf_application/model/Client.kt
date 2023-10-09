package dio.ssf_application.model

import jakarta.persistence.*
import jakarta.validation.constraints.Pattern

@Entity
data class Client(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long,
  var nome: String,
  @ManyToOne(fetch = FetchType.LAZY)
  var endereco: Address,
) {}