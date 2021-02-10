package br.edu.ifmt.controledeacesso.controllers

import br.edu.ifmt.controledeacesso.services.EmailService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsável pelos endpoints de recebimento de e-mail
 * utilizado como WebHook pelo mailgun.com.
 * <br>
 * URI: <b>/email<b/>
 *
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@RestController
@RequestMapping("/email")
class EmailController(private val emailService: EmailService) {
  @PostMapping
  fun receive(
    @RequestParam(value = "from") from: String,
    @RequestParam(value = "subject") subject: String,
    @RequestParam(value = "body-plain") body: String
  ): String {

    println("FROM: $from")
    println("SUBJECT: $subject")
    println("-".repeat(30))
    println(body)
    println("-".repeat(30))
    emailService.createVisita(from, subject, body)
    return "post received"
  }

}