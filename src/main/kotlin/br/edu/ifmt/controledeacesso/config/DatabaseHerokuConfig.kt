package br.edu.ifmt.controledeacesso.config

import br.edu.ifmt.controledeacesso.domain.services.DatabaseService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@Configuration
@Profile("heroku")
@PropertySource("classpath:application.properties")
class DatabaseHerokuConfig(
  private val service: DatabaseService,
) {
  @Value("\${spring.jpa.hibernate.ddl-auto}")
  private lateinit var strategy: String

  @Bean
  fun populateDatabase(): Boolean {

    if("create" != strategy) return false

    service.createUsuarios()
    return true
  }
}