package br.edu.ifmt.controledeacesso.config

import net.sargue.mailgun.Mail
import net.sargue.mailgun.MailBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import net.sargue.mailgun.Configuration as MailGunConfig

/**
 * Define beans e configurações relacionadas ao MailGun
 * Fornece uma instância de um MailBuilder utilizado para
 * enviar e-mails ao serviço do MailGun
 *
 * @project cae-api
 * @author daohn on 27/01/2021
 */
@Configuration
@PropertySource("classpath:application.properties")
class MailGunConfig {
  @Value("\${mailgun.api.domain}")
  lateinit var apiDomain: String
    @Bean get

  @Value("\${mailgun.api.key}")
  lateinit var apiKey: String
    @Bean get

  @Bean
  fun mailBuilder(): MailBuilder = Mail.using(
    MailGunConfig()
      .domain(apiDomain)
      .apiKey(apiKey)
      .from("Suporte CAE-IFMT", "suporte@$apiDomain")
  )
}