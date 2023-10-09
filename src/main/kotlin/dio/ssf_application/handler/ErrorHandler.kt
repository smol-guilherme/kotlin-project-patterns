package dio.ssf_application.handler

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ErrorHandler {

  @ExceptionHandler
  fun <R> handle(action: R): R? {
    try {
      return action
      // aq entram os erros customizados
    } catch (exc: HttpStatusCodeException) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST, exc.message)
    }
  }
}