package dio.ssf_application.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, Long> {
}
