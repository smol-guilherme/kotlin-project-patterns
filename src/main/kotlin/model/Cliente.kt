package model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
class Cliente(
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  val id: Long,
  var nome: String,
  @ManyToOne
  var endereco: String,
) {}