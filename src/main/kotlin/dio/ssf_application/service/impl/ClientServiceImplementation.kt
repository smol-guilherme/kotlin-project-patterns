package dio.ssf_application.service.impl

import dio.ssf_application.handler.errors.InsufficientInformationException
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
    return repository.findById(id)
  }

  private fun saveClientWithAddress(cli: Client) {
    validate(cli)
    val cep: String = cli.endereco.cep
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
    if(data.nome.isBlank()) throw IllegalArgumentException("nome (name)")
    if(!cep.matches(cepRegex)) throw InsufficientInformationException("CEP")
    if(!data.nome.matches(nameRegex)) throw InsufficientInformationException("nome (name)")
  }
}