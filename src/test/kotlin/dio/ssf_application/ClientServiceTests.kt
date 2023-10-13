package dio.ssf_application

import com.ninjasquad.springmockk.MockkBean
import dio.ssf_application.factory.AddressFixture
import dio.ssf_application.factory.ClientFixture
import dio.ssf_application.handler.errors.ClientNotFoundException
import dio.ssf_application.handler.errors.InsufficientInformationException
import dio.ssf_application.model.AddressRepository
import dio.ssf_application.model.ClientRepository
import dio.ssf_application.service.ViaCepService
import dio.ssf_application.service.impl.ClientServiceImplementation
import feign.FeignException
import feign.Request
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import java.nio.charset.StandardCharsets
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

  private val randomCep = Random.nextInt(90000000, 99999999).toString()
  private val randomId = Random.nextLong(1, 100)
  private val factoredAddress = AddressFixture.address(randomCep)
  private val factoredClient = ClientFixture.client(id = randomId, address = factoredAddress)

  @BeforeEach
  private fun startup() {
    repository = mockk<ClientRepository>()
    address = mockk<AddressRepository>()
    viaCep = spyk<ViaCepService>()
    service = spyk(ClientServiceImplementation(repository, address, viaCep))
  }

  @AfterEach
  private fun cleanup() {
    clearAllMocks()
  }

  @Test
  fun whenGetClient_thenReturnClient() {


    every { repository.findById(any()) } returns Optional.of(ClientFixture.client(randomId, address = factoredAddress))
    service.findById(randomId)

    assertAll(
      { verify(exactly = 1) { repository.findById(randomId) } }
    )
  }

  @Test
  fun whenGetClient_thenReturnNothing() {
    every { repository.findById(randomId) } returns Optional.empty()

    assertThrows<ClientNotFoundException> { service.findById(randomId) }
  }

  @Test
  fun whenInsertClient_thenReturnSuccess() {
    every { repository.save(factoredClient) } returns factoredClient
    every { address.findById(randomCep) } returns Optional.of(factoredAddress)
    service.add(factoredClient)

    assertAll(
      { verify(exactly = 1) { repository.save(factoredClient) } },
    )
  }

  @Test
  fun whenInsertClientWithInvalidAddress_thenReturnsError() {
    //    Request object below taken from OpenFeign tests on GitHub and refactored to Kotlin
    val request = Request.create(
      Request.HttpMethod.GET,
      "/home",
      emptyMap(),
      "data".toByteArray(StandardCharsets.UTF_16BE),
      StandardCharsets.UTF_16BE,
      null
    )
    //    mockkConstructor(FeignException.BadRequest::class) does not solve the issue I was having
    val exception = FeignException.BadRequest("Bad request", request, request.body(), request.headers())

    every { address.findById(randomCep) } returns Optional.empty()
    every { viaCep.enquiryCep(randomCep) } throws exception

    assertThrows<FeignException> { service.add(factoredClient) }
  }

  @Test
  fun whenInsertClientWithInvalidData_thenReturnsError() {
    val invalidClient = ClientFixture.client(randomId, "", factoredAddress)
    every { address.findById(randomCep) } returns Optional.of(factoredAddress)

    assertThrows<InsufficientInformationException> { service.add(invalidClient) }
  }

  @Test
  fun whenClientExists_thenUpdatesInformation_succeeds() {
    val newClient = ClientFixture.client(randomId, "New valid name", factoredAddress)
    every { address.findById(randomCep) } returns Optional.of(factoredAddress)
    every { repository.findById(randomId) } returns Optional.of(factoredClient)
    every { repository.save(any()) } returns factoredClient
    service.add(factoredClient)
    service.update(randomId, newClient)

    assertAll(
      { verify(exactly = 1) { service.update(randomId, newClient) } }
    )
  }

  @Test
  fun whenClientExists_thenUpdatesInformation_fails() {
    val newInvalidClient = ClientFixture.client(randomId, "Badname", factoredAddress)
    every { address.findById(randomCep) } returns Optional.of(factoredAddress)
    every { repository.findById(randomId) } returns Optional.of(factoredClient)
    every { repository.save(any()) } returns factoredClient
    service.add(factoredClient)

    assertThrows<IllegalArgumentException> { service.update(randomId, newInvalidClient) }
  }
}
