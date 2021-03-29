package br.edu.ifmt.controledeacesso.api.security.filters


import br.edu.ifmt.controledeacesso.api.controllers.dto.UsuarioCredenciaisDto
import br.edu.ifmt.controledeacesso.api.controllers.dto.UsuarioDto
import br.edu.ifmt.controledeacesso.api.security.utils.JWTUtil
import br.edu.ifmt.controledeacesso.api.security.utils.write
import br.edu.ifmt.controledeacesso.domain.entities.Usuario
import br.edu.ifmt.controledeacesso.exceptions.DefaultErrorMessage
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
class JWTAuthenticationFilter(
  authenticationManager: AuthenticationManager?,
  private val jwtUtil: JWTUtil,
  private val modelMapper: ModelMapper
) :
  UsernamePasswordAuthenticationFilter(authenticationManager) {

  /**
   * Realiza tentativa de autenticação na api através do DTO UsuarioCredenciaisDto validando o
   * login e senha no corpo da requisição.
   */
  override fun attemptAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?
  ): Authentication {
    val usuarioCredenciaisDto = ObjectMapper().readValue(request?.inputStream, UsuarioCredenciaisDto::class.java)

    if (StringUtils.isEmpty(usuarioCredenciaisDto.login) || StringUtils.isEmpty(usuarioCredenciaisDto.senha))
      throw BadCredentialsException("Invalid username/password")

    val authentication = UsernamePasswordAuthenticationToken(usuarioCredenciaisDto.login, usuarioCredenciaisDto.senha)
    return authenticationManager.authenticate(authentication)
  }

  /**
   * Retorna um UsuarioDto após realizar com sucesso a autenticação na api.
   */
  override fun successfulAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    chain: FilterChain?,
    authentication: Authentication?
  ) {
    val usuarioData = authentication?.principal as Usuario
    val usuarioDto = modelMapper.map(usuarioData, UsuarioDto::class.java)
    usuarioDto.token = jwtUtil.createToken(usuarioData)

    response?.let {
      it.addHeader("Authorization", "Bearer " + usuarioDto.token)
      write(it, HttpStatus.OK, usuarioDto.toJson())
    }
  }

  /**
   * Retorna uma mensagem de erro após falhar na autenticação da api.
   */
  @Throws(IOException::class, ServletException::class)
  override fun unsuccessfulAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    error: AuthenticationException?
  ) {
    response?.let {
      write(
        it,
        HttpStatus.UNAUTHORIZED,
        DefaultErrorMessage(
          status = HttpStatus.UNAUTHORIZED.value(),
          error = HttpStatus.UNAUTHORIZED.name,
          message = "Não autorizado",
          path = request?.requestURI ?: "",
        ).toJson()
      )
    }
  }

}