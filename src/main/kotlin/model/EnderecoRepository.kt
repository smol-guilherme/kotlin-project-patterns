package model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EnderecoRepository : CrudRepository<Endereco, String> {

}
