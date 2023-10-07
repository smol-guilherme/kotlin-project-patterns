package service

import model.Endereco
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "Viacep", url = "https://viacep.com.br/ws")
interface ViaCepService {

  @GetMapping("/{cep}/json/")
  fun consultarCep(@PathVariable("cep") cep: String): Endereco
}