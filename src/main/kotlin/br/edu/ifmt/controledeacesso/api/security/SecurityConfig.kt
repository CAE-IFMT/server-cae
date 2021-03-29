package br.edu.ifmt.controledeacesso.api.security

import br.edu.ifmt.controledeacesso.api.security.filters.JWTAuthenticationFilter
import br.edu.ifmt.controledeacesso.api.security.filters.JWTAuthorizationFilter
import br.edu.ifmt.controledeacesso.api.security.utils.JWTUtil
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class SecurityConfig(
  @Qualifier("usuarioService")
  val usuarioService: UserDetailsService,
  val encoder: BCryptPasswordEncoder,
  val jwtUtil: JWTUtil,
  val mapper: ModelMapper,
  val accessDeniedHandlerImpl: AccessDeniedHandler,
  val unauthorizedHandlerImpl: AuthenticationEntryPoint,
) : WebSecurityConfigurerAdapter() {

  private val publicResourcesPOST = arrayOf("/email/**")

  private val publicResourcesALL = arrayOf<String>()

  /**
   * Adiciona configurações de segurança para autenticação e autorização
   */
  override fun configure(http: HttpSecurity) {
    http
      .authorizeRequests()
      // Remove autenticação para endpoints da lista publicResourcePOST que utilizam o verbo POST
      .antMatchers(HttpMethod.POST, *publicResourcesPOST).permitAll()
      // Remove autenticação para endpoints da lista publicResourceALL
      .antMatchers(*publicResourcesALL).permitAll()
      .anyRequest().authenticated()
      .and().csrf().disable()
      // Adiciona filtro para autenticação
      .addFilter(JWTAuthenticationFilter(authenticationManager(), jwtUtil, mapper))
      // Adiciona filtro para autorização
      .addFilter(JWTAuthorizationFilter(authenticationManager(), jwtUtil, usuarioService))
      .exceptionHandling()
      // Adiciona handler para acesso negado
      .accessDeniedHandler(accessDeniedHandlerImpl)
      // Adiciona handler para acesso não autorizado
      .authenticationEntryPoint(unauthorizedHandlerImpl)
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  /**
   * Adiciona dependência para criptografar e descriptografar
   */
  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(usuarioService).passwordEncoder(encoder)
  }
}