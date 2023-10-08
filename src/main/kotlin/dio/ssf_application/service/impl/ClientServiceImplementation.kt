package dio.ssf_application.service.impl

import dio.ssf_application.model.Client
import dio.ssf_application.model.ClientRepository
import dio.ssf_application.model.Address
import dio.ssf_application.model.AddressRepository
import org.springframework.stereotype.Service
import dio.ssf_application.service.ClientService
import dio.ssf_application.service.ViaCepService
import org.springframework.beans.factory.annotation.Autowired
import java.util.Optional

@Service
class ClientServiceImplementation(
  @Autowired private val repository: ClientRepository,
  @Autowired private val addresses: AddressRepository,
  @Autowired private val viaCep: ViaCepService,
) : ClientService {

  override fun findAll(): Iterable<Client> {
    return repository.findAll()
  }

  override fun add(cli: Client) {
    if(cli.nome.isBlank()) throw IllegalArgumentException("nome (name) field required")
    saveClientWithAddress(cli)
  }

  override fun update(id: Long, cli: Client) {
    val clientFound = findById(id)
    if(clientFound.isPresent) {
      saveClientWithAddress(cli)
    }
  }

  override fun delete(id: Long) {
    repository.deleteById(id)
  }

  override fun findById(id: Long): Optional<Client> {
    return repository.findById(id)
  }

  private fun saveClientWithAddress(cli: Client) {
    try {
      val cep: String = cli.endereco.cep
      if(cep.isBlank()) throw IllegalArgumentException()
      val address = addresses.findById(cep).orElseGet {
        val newAddress: Address = viaCep.enquiryCep(cep)
        addresses.save(newAddress)
        return@orElseGet newAddress
      }
      cli.endereco = address
      repository.save(cli)
    } catch (e: IllegalArgumentException) {
      println("CEP required")
    }
  }
}