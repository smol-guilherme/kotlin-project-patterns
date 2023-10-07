package model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClienteRepository : CrudRepository<Cliente, Long> {

}
