package br.edu.ifmt.controledeacesso.api.security.filters


import br.edu.ifmt.controledeacesso.api.security.utils.JWTUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
class JWTAuthorizationFilter(
  authenticationManager: AuthenticationManager?,
  private val jwtUtil: JWTUtil,
  private val userDetailsService: UserDetailsService
) :
  BasicAuthenticationFilter(authenticationManager) {

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    chain: FilterChain
  ) {

    val headerToken = request.getHeader("Authorization")

    if (headerToken != null && headerToken.startsWith("Bearer ")) {
      val token = headerToken.substring(7)
      val authentication = createAuthentication(token)
      if (authentication != null) {
        SecurityContextHolder.getContext().authentication = authentication
      }
    }
    chain.doFilter(request, response)
  }

  @Throws(UsernameNotFoundException::class)
  private fun createAuthentication(token: String): UsernamePasswordAuthenticationToken? {
    // TODO: testar uma implementação alternativa lançando AccessDeniedException
    if (jwtUtil.isValidToken(token)) {
      val username = jwtUtil.getLogin(token)
      val user = userDetailsService.loadUserByUsername(username)
      return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
    return null
  }
}