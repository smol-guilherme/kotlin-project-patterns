package dio.ssf_application.factory

import dio.ssf_application.model.Address
import jakarta.annotation.Nullable

class AddressFixture {

  companion object {
    fun address(
      cep: String,
      logradouro: String? = "",
      complemento: String? = "",
      bairro: String? = "",
      localidade: String? = "",
      uf: String? = "",
      ibge: String? = "",
      gia: String? = "",
      ddd: String? = "",
      siafi: String? = ""
    ): Address {
      return Address(
        cep = cep,
        logradouro = logradouro,
        complemento = complemento,
        bairro = bairro,
        localidade = localidade,
        uf = uf,
        ibge = ibge,
        gia = gia,
        ddd = ddd,
        siafi = siafi
      )
    }
  }
}