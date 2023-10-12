package dio.ssf_application


import com.ninjasquad.springmockk.MockkBean
import dio.ssf_application.factory.AddressFixture
import dio.ssf_application.factory.ClientFixture
import dio.ssf_application.handler.errors.ClientNotFoundException
import dio.ssf_application.model.AddressRepository
import dio.ssf_application.model.ClientRepository
import dio.ssf_application.service.ViaCepService
import dio.ssf_application.service.impl.ClientServiceImplementation
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.random.Random

@ExtendWith(MockKExtension::class)
internal class ClientServiceTests() {

  private lateinit var service: ClientServiceImplementation

  @MockkBean
  private lateinit var repository: ClientRepository

  @MockkBean
  private lateinit var address: AddressRepository

  @MockkBean
  private lateinit var viaCep: ViaCepService

  @BeforeEach
  private fun startup() {
    repository = mockk<ClientRepository>()
    address = mockk<AddressRepository>()
    viaCep = mockk<ViaCepService>()
  }

  @AfterEach
  private fun cleanup() {
    clearAllMocks()
  }

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

    every { repository.findById(randomId) } returns Optional.empty()

    assertThrows<ClientNotFoundException> { service.findById(randomId) }
  }

  @Test
  fun whenInsertClient_thenReturnSuccess() {
    service = spyk(ClientServiceImplementation(repository, address, viaCep))
    val randomId = Random.nextLong(1, 100)
    val randomCep = Random.nextInt(90000000, 99999999).toString()

    val factoredAddress = AddressFixture.address(randomCep)
    val userCreated = ClientFixture.client(id = randomId, address = factoredAddress)
    every { repository.save(userCreated) } returns userCreated
    every { address.findById(randomCep) } returns Optional.of(factoredAddress)
    service.add(userCreated)

    assertAll(
      { verify(exactly = 1) { repository.save(userCreated) } },
    )
  }
}
