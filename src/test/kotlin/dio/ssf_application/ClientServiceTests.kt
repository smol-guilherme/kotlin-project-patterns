package dio.ssf_application


import com.ninjasquad.springmockk.MockkBean
import dio.ssf_application.factory.AddressFixture
import dio.ssf_application.factory.ClientFixture
import dio.ssf_application.model.Address
import dio.ssf_application.model.AddressRepository
import dio.ssf_application.model.ClientRepository
import dio.ssf_application.service.ViaCepService
import dio.ssf_application.service.impl.ClientServiceImplementation
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
internal class ClientServiceTests() {

  private lateinit var service: ClientServiceImplementation

  @MockK
  private lateinit var repository: ClientRepository

  @MockK
  private lateinit var address: AddressRepository

  @MockK
  private lateinit var viaCep: ViaCepService

  @Test
  fun whenGetClient_thenReturnClient() {
    // mockar o retorno ao acesso da api pra buscar o cep
    // testar de modo regular a adicao/atualizacao/remocao de cliente
    // isso faz bem mais sentido mas eu vou anotar igual pra não esquecer
    // pq o alzheimer é foda

    service = spyk(ClientServiceImplementation(repository, address, viaCep))
    val randomId = Random.nextLong(1, 100)
    val randomCep = Random.nextInt(90000000, 99999999).toString()

    every { repository.findById(any()) } returns Optional.of(ClientFixture.client(id = randomId, address = AddressFixture.address(randomCep)))
    service.findById(randomId)

    assertAll(
      { verify(exactly = 1) { repository.findById(randomId) } }
    )
  }

  @Test
  fun whenGetClient_thenReturnNothing() {
    service = spyk(ClientServiceImplementation(repository, address, viaCep))
    val randomId = Random.nextLong(1, 100)
    val randomCep = Random.nextInt(90000000, 99999999).toString()

    every { repository.findById(any()) } returns Optional.empty()
    service.findById(randomId)

    assertAll(
      { verify(exactly = 1) { repository.findById(randomId) } }
    )
  }
}
