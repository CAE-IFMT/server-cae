package br.edu.ifmt.controledeacesso.exceptions

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Created by daohn on 15/02/2021
 * @author daohn
 * @since 15/02/2021
 * @version 1.0.0
 */
@RestControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {
  @ExceptionHandler(EmptyResultDataAccessException::class)
  fun errorNotFound(exception: Exception): ResponseEntity<Any> {
    return ResponseEntity.notFound().build()
  }

  @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
  fun errorBadRequest(exception: Exception): ResponseEntity<Any> {
    return ResponseEntity.badRequest().build()
  }

  @ExceptionHandler(AccessDeniedException::class)
  fun errorForbidden(exception: Exception, request: WebRequest): ResponseEntity<Any> {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
      DefaultErrorMessage(
        status = HttpStatus.FORBIDDEN.value(),
        error = HttpStatus.FORBIDDEN.name,
        message = "Acesso negado",
        path = (request as ServletWebRequest).request.requestURI,
      )
    )
  }

  override fun handleHttpRequestMethodNotSupported(
    ex: HttpRequestMethodNotSupportedException,
    headers: HttpHeaders,
    status: HttpStatus,
    request: WebRequest
  ): ResponseEntity<Any> {

    var message = "${ex.method} não é suportado para essa requisição. Os métodos suportados são: "

    ex.supportedMethods?.forEach { method -> message += "$method " }

    return ResponseEntity
      .status(HttpStatus.METHOD_NOT_ALLOWED)
      .body(
        DefaultErrorMessage(
          System.currentTimeMillis(),
          status.value(),
          status.name,
          message,
          (request as ServletWebRequest).request.requestURI,
        )
      )
  }
}