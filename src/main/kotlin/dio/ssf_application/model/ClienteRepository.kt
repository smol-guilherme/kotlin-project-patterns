package dio.ssf_application.model

import dio.ssf_application.model.Cliente
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository : CrudRepository<Cliente, Long> {

}
