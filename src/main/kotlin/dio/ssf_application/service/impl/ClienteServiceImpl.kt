package dio.ssf_application.service.impl

import dio.ssf_application.model.Cliente
import org.springframework.stereotype.Service
import dio.ssf_application.service.ClienteService

@Service
class ClienteServiceImpl() : ClienteService {

  override fun findAll(): Iterable<Cliente> {
    return TODO()
  }

  override fun add(cli: Cliente) {
    return TODO("Not yet implemented")
  }

  override fun delete(id: Long) {
    TODO("Not yet implemented")
  }

  override fun findById(id: Long): Cliente {
    TODO("Not yet implemented")
  }

  override fun update(id: Long, cli: Cliente) {
    TODO("Not yet implemented")
  }
}