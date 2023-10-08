package dio.ssf_application.service

import dio.ssf_application.model.Cliente

interface ClienteService {

  fun findAll(): Iterable<Cliente>

  fun findById(id: Long): Cliente

  fun add(cli: Cliente)

  fun update(id: Long, cli: Cliente)

  fun delete(id: Long)

}