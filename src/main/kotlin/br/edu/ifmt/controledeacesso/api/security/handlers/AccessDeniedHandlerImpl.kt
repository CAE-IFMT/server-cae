package br.edu.ifmt.controledeacesso.api.security.handlers

import br.edu.ifmt.controledeacesso.api.security.utils.write
import br.edu.ifmt.controledeacesso.exceptions.DefaultErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
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
class AccessDeniedHandlerImpl : AccessDeniedHandler {

  /**
   * Função responsável por tratar erro de acesso negado gerando uma resposta do tipo
   */
  override fun handle(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    exception: AccessDeniedException?
  ) {
    val authentication = SecurityContextHolder.getContext().authentication
    if (authentication != null) {
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


}