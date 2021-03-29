package br.edu.ifmt.controledeacesso.api.security.handlers

import br.edu.ifmt.controledeacesso.api.security.utils.write
import br.edu.ifmt.controledeacesso.exceptions.DefaultErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@Component
class UnauthorizedHandlerImpl : AuthenticationEntryPoint {
  /**
   * Função callback chamada após encontrar uma exceção do tipo AuthenticationException. Adiciona
   * um DefaultErrorMessage contendo mensagem de erro.
   */
  override fun commence(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    exception: AuthenticationException?
  ) {
    response?.let {
      write(
        it,
        HttpStatus.FORBIDDEN,
        DefaultErrorMessage(
          status = HttpStatus.FORBIDDEN.value(),
          error = HttpStatus.FORBIDDEN.name,
          message = "Acesso negado",
          path = request?.requestURI ?: "",
        ).toJson()
      )
    }
  }
}