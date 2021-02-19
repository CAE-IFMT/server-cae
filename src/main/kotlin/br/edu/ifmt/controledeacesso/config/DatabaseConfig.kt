package br.edu.ifmt.controledeacesso.config

import br.edu.ifmt.controledeacesso.domain.services.DatabaseService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Configuration
@Profile("docker", "localhost")
@PropertySource("classpath:application.properties")
class DatabaseConfig(
  private val service: DatabaseService,
) {

  @Value("\${spring.jpa.hibernate.ddl-auto}")
  private lateinit var strategy: String

  @Bean
  fun populateDatabase(): Boolean {

    if("create" != strategy) return false

    service.instantiateDatabase()
    return true
  }

}