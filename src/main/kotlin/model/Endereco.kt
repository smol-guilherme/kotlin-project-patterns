package model

import jakarta.annotation.Nullable
import jakarta.persistence.Id

class Endereco(
  @Id
  val cep: String,
  var logradouro: String,
  @Nullable
  var complemento: String,
  var bairro: String,
  var localidade: String,
  var uf: String,
  var ibge: String,
  var gia: String,
  var ddd: String,
  var siafi: String
) {
}