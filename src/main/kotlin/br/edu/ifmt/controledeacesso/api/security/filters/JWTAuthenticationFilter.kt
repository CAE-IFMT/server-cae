package br.edu.ifmt.controledeacesso.api.security.filters


import br.edu.ifmt.controledeacesso.api.security.utils.JWTUtil
import br.edu.ifmt.controledeacesso.api.security.utils.write
import br.edu.ifmt.controledeacesso.domain.dto.UsuarioCredenciaisDTO
import br.edu.ifmt.controledeacesso.domain.dto.UsuarioDTO
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

  override fun attemptAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?
  ): Authentication {
    val login = ObjectMapper().readValue(request?.inputStream, UsuarioCredenciaisDTO::class.java)

    if (StringUtils.isEmpty(login.login) || StringUtils.isEmpty(login.senha))
      throw BadCredentialsException("Invalid username/password")

    val authentication = UsernamePasswordAuthenticationToken(login.login, login.senha)
    return authenticationManager.authenticate(authentication)
  }

  override fun successfulAuthentication(
    request: HttpServletRequest?,
    response: HttpServletResponse?,
    chain: FilterChain?,
    authentication: Authentication?
  ) {
    val usuarioData = authentication?.principal as Usuario
    val usuarioDTO = modelMapper.map(usuarioData, UsuarioDTO::class.java)
    usuarioDTO.token = jwtUtil.createToken(usuarioData)

    response?.let {
      it.addHeader("Authorization", "Bearer " + usuarioDTO.token)
      write(it, HttpStatus.OK, usuarioDTO.toJson())
    }
  }

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