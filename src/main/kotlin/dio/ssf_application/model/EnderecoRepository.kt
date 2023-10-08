package dio.ssf_application.model

import dio.ssf_application.model.Endereco
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EnderecoRepository : CrudRepository<Endereco, String> {

}
