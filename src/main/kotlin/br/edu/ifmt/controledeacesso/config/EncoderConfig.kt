package br.edu.ifmt.controledeacesso.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * @project carros-api-kt
 * @author daohn on 03/02/2021
 */
@Configuration
class EncoderConfig {
  @Bean
  fun encoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}