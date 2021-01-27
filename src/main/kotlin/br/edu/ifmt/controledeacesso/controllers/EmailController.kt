package br.edu.ifmt.controledeacesso.controllers

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsável pelos endpoints de recebimento de e-mail
 * utilizado como WebHook pelo mailgun.com.
 * <br>
 * URI: <br>/email<b/>
 *
 * @project cae-api
 * @author daohn on 24/01/2021
 */
@RestController
@RequestMapping("/email")
class EmailController {
}