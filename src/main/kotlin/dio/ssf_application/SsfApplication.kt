package dio.ssf_application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class SsfApplication

fun main(args: Array<String>) {
  runApplication<SsfApplication>(*args)
}
