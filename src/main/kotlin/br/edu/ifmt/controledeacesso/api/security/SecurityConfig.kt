package br.edu.ifmt.controledeacesso.api.security

import br.edu.ifmt.controledeacesso.api.security.filters.JWTAuthenticationFilter
import br.edu.ifmt.controledeacesso.api.security.filters.JWTAuthorizationFilter
import br.edu.ifmt.controledeacesso.api.security.handlers.AccessDeniedHandler
import br.edu.ifmt.controledeacesso.api.security.handlers.UnauthorizedHandler
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
  val accessDeniedHandler: AccessDeniedHandler,
  val unauthorizedHandler: UnauthorizedHandler,
) : WebSecurityConfigurerAdapter() {
  private val publicResourcesPOST = arrayOf(
    "/email/**"
  )
  private val publicResourcesALL = arrayOf<String>(
  )

  override fun configure(http: HttpSecurity) {
    http
      .authorizeRequests()
      .antMatchers(HttpMethod.POST, *publicResourcesPOST).permitAll()
      .antMatchers(*publicResourcesALL).permitAll()
      .anyRequest().authenticated()
      .and().csrf().disable()
      .addFilter(JWTAuthenticationFilter(authenticationManager(), jwtUtil, mapper))
      .addFilter(JWTAuthorizationFilter(authenticationManager(), jwtUtil, usuarioService))
      .exceptionHandling()
      .accessDeniedHandler(accessDeniedHandler)
      .authenticationEntryPoint(unauthorizedHandler)
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(usuarioService).passwordEncoder(encoder)
  }
}