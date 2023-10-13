package dio.ssf_application.factory

import dio.ssf_application.model.Address
import dio.ssf_application.model.Client
import kotlin.random.Random

class ClientFixture {

  companion object {

    fun client(
      id: Long = Random.nextLong(),
      name: String = "Valid Name",
      address: Address
    ): Client {
      return Client(id = id, nome = name, endereco = address)
    }
  }
}