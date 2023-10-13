package dio.ssf_application.service.impl

import dio.ssf_application.handler.errors.ClientNotFoundException
import dio.ssf_application.handler.errors.InsufficientInformationException
import dio.ssf_application.model.Address
import dio.ssf_application.model.AddressRepository
import dio.ssf_application.model.Client
import dio.ssf_application.model.ClientRepository
import dio.ssf_application.service.ClientService
import dio.ssf_application.service.ViaCepService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientServiceImplementation(
  @Autowired private val repository: ClientRepository,
  @Autowired private val addresses: AddressRepository,
  @Autowired private val viaCep: ViaCepService
) : ClientService {

  override fun findAll(): Iterable<Client> {
    return repository.findAll()
  }

  override fun add(cli: Client) {
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
    val client = repository.findById(id)
    if(client.isEmpty) throw ClientNotFoundException("Client by id $id")
    return client
  }

  private fun saveClientWithAddress(cli: Client) {
    val cep: String = cli.endereco.cep
    validate(cli)
    val address = addresses.findById(cep).orElseGet {
      val newAddress: Address = viaCep.enquiryCep(cep)
      addresses.save(newAddress)
      return@orElseGet newAddress
    }
    cli.endereco = address
    repository.save(cli)
  }

  private fun validate(data: Client) {
    val nameRegex = Regex("^((\\w+\\s\\w+)\\D+)\$")
    val cepRegex = Regex("(^\\d{8}$)|(^\\d{5}-\\d{3}$)")
    val cep: String = data.endereco.cep
    if(cep.isBlank()) throw IllegalArgumentException("CEP")
    if(data.nome.isBlank()) throw InsufficientInformationException("nome (name)")
    if(!cep.matches(cepRegex)) throw InsufficientInformationException("CEP")
    if(!data.nome.matches(nameRegex)) throw IllegalArgumentException("nome (name)")
  }
}