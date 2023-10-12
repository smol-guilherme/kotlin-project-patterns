package dio.ssf_application.factory

import dio.ssf_application.model.Address
import dio.ssf_application.model.Client
import kotlin.random.Random

class ClientFixture {

  companion object {
    // mock do endereco pq ele faz parte de uma api externa,
    // a principio isso não deve resultar em erros durante o teste
    // caso eu esteja errado, preciso rever a instanciação do mock

    // sobre este componente, isso é um Builder, um padrão de projeto em java
    // assim, meu projeto que já usa Singleton, Strategy e Facade
    // agora também implementa Builder Entity para instanciar algumas classes
    // durante os testes. Isso é importante de lembrar.

    // também citar o guia de onde eu estou tirando informações para fazer os testes
    // assim como o guia diz, eu não devo testar codigo que eu não fiz
    // isso se aplica ao acesso a api externa (por isso o mock) e tambem
    // se aplicar aos repositorios, pois eles são gerenciados pelo Spring
    // e eu apenas implementei o servico para fazer validacao de dados
    // e outras lógicas antes da atuação do spring.

    fun client(
      id: Long = Random.nextLong(),
      name: String = "Valid Name",
      address: Address
    ): Client {
      return Client(id = id, nome = name, endereco = address)
    }
  }
}