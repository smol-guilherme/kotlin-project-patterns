package dio.ssf_application.service

import dio.ssf_application.model.Client
import java.util.*

interface ClientService {

  fun findAll(): Iterable<Client>

  fun findById(id: Long): Optional<Client>

  fun add(cli: Client)

  fun update(id: Long, cli: Client)

  fun delete(id: Long)

}