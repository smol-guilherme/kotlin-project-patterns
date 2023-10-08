package dio.ssf_application.controller

import dio.ssf_application.model.Client
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import dio.ssf_application.service.impl.ClientServiceImplementation
import org.springframework.beans.factory.annotation.Autowired
import java.util.Optional

@RestController
@RequestMapping("/clientes")
class ClientRestController(
  @Autowired
  private val service: ClientServiceImplementation
) {

  @GetMapping
  fun findAll(): ResponseEntity<Iterable<Client>> {
    return ResponseEntity.ok(service.findAll())
  }

  @GetMapping("/{id}")
  fun findById(id: Long): ResponseEntity<Optional<Client>> {
    return ResponseEntity.ok(service.findById(id))
  }

  @PostMapping
  fun insert(@RequestBody newCli: Client): ResponseEntity<Client> {
    service.add(newCli)
    return ResponseEntity.ok(newCli)
  }

  @PutMapping("/{id}")
  fun update(@PathVariable id: Long, @RequestBody client: Client): ResponseEntity<Client> {
    service.update(id, client)
    return ResponseEntity.ok(client)
  }

  @DeleteMapping("/{id}")
  fun removeById(@PathVariable id: Long): ResponseEntity<Void> {
    service.delete(id)
    return ResponseEntity.ok().build()
  }
}