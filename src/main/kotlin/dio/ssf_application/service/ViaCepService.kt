package dio.ssf_application.service

import dio.ssf_application.model.Address
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
interface ViaCepService {

  @GetMapping("/{cep}/json/")
  fun enquiryCep(@PathVariable("cep") cep: String): Address
}