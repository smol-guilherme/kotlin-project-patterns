package dio.ssf_application.controller

import dio.ssf_application.model.Cliente
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import dio.ssf_application.service.impl.ClienteServiceImpl

@RestController
@RequestMapping("/clientes")
class ClienteRestController() {
  private val service: ClienteServiceImpl = ClienteServiceImpl()

  @GetMapping
  fun buscarTodos(): ResponseEntity<Iterable<Cliente>> {
    return ResponseEntity.ok(service.findAll())
  }

  @GetMapping("/{id}")
  fun buscarPorId(id: Long): ResponseEntity<Cliente> {
    return ResponseEntity.ok(service.findById(id))
  }

  @PostMapping
  fun inserir(@RequestBody novo: Cliente): ResponseEntity<Cliente> {
    service.add(novo)
    return ResponseEntity.ok(novo)
  }

  @PutMapping("/{id}")
  fun atualizar(@PathVariable id: Long, @RequestBody cliente: Cliente): ResponseEntity<Cliente> {
    service.update(id, cliente)
    return ResponseEntity.ok(cliente)
  }

  @DeleteMapping("/{id}")
  fun deletar(@PathVariable id: Long): ResponseEntity<Void> {
    service.delete(id)
    return ResponseEntity.ok().build()
  }
}