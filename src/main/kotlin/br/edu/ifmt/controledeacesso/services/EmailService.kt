package br.edu.ifmt.controledeacesso.services

import br.edu.ifmt.controledeacesso.models.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitaDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitanteDTO
import mu.KotlinLogging
import net.glxn.qrgen.core.image.ImageType
import net.glxn.qrgen.javase.QRCode
import net.sargue.mailgun.Configuration
import net.sargue.mailgun.Mail
import org.springframework.stereotype.Service
import java.io.File
import java.util.regex.Pattern
import java.util.stream.Collectors


/**
 * Interface que define regras de negócio relacionadas
 * ao envio e recebimento de e-mail.
 *
 * @project cae-api
 * @author daohn on 26/01/2021
 */
@Service
class EmailService(
  private val visitaService: VisitaService,
  private val parserService: EmailParserService,
) {
  private val logger = KotlinLogging.logger {  }
  private val apiDomain = "sandbox5538a24e2fa84912aee78c7f4310084a.mailgun.org"
  private val apiKey = "cc1b19fdc04e0c93883adac0c05e73ec-07bc7b05-d6b73865"

  private fun createQRCode(visita: VisitaDTO): File {
    return QRCode.from(visita.visitante.nome)
      .to(ImageType.JPG)
      .withCharset("UTF-8")
      .withSize(400, 400)
      .file()
  }

//  private fun sendEmail(email: String) {
//    val qrcode = createQRCode()
//
//    builder
//      .to(email)
//      .subject("QRCode de autenticação")
//      .text("Permissão de entrada para o Instituto Federal de Mato Grosso")
//      .multipart()
//      .attachment(qrcode)
//      .build()
//      .send()
//  }

  fun createVisita(from: String, subject: String, body: String) {
    try {
      val dto = this.buildDTO(body, from)

      logger.info { "Criado DTO: $dto" }

      val visita = visitaService.save(dto)

      logger.info { "Visita persistida com sucesso: $visita" }

      sendEmailToProfessor(from, visita)
      sendEmailToVisitante(visita)

    } catch (exception: Exception) {
      print(exception.message)
    }
  }

  private fun sendEmailToProfessor(from: String, visita: VisitaDTO) {
    val email = this.extractEmail(from)
    logger.info { "Enviando email para $email" }
    val response =
      Mail.using(
        Configuration()
          .domain(apiDomain)
          .apiKey(apiKey)
          .from("Suporte CAE-IFMT", "suporte@$apiDomain")
      ).to(email)
        .subject("Permissão de entrada no IFMT")
        .text(
          "Permissão de entrada para o Instituto Federal de Mato Grosso agendada " +
              "para ${visita.visitante.nome} por ${visita.professor.nome} no dia ${visita.data}."
        )
        .build()
        .send()
    logger.info { "Email enviado ${response.responseMessage()}" }
  }

  private fun sendEmailToVisitante(visita: VisitaDTO) {
    val qrCode = this.createQRCode(visita)

    logger.info { "Email enviado para ${visita.visitante.email}" }

    val response = Mail.using(
      Configuration()
        .domain(apiDomain)
        .apiKey(apiKey)
        .from("Suporte CAE-IFMT", "suporte@$apiDomain")
    )
      .to(visita.visitante.email)
      .subject("QRCode para entrada no IFMT")
      .text("Permissão de entrada para o Instituto Federal de Mato Grosso")
      .multipart()
      .attachment(qrCode)
      .build()
      .send()
    logger.info { "Email enviado ${response.responseMessage()}" }
  }

//  private fun writeEmail(email: String, subject: String, text: String): MailBuilder {
//    return builder.to(email)
//      .subject(subject)
//      .text(text)
//  }

  private fun extractEmail(from: String): String {
    val split = Pattern.compile("[<>]")
      .splitAsStream(from)
      .collect(Collectors.toList())
    val email = split[1]
    if (!email.contains("@"))
      throw IllegalStateException("Não será possível enviar notificação por email $email")
    return email
  }

  private fun buildDTO(body: String, from: String): VisitaSaveDTO {
    val properties = parserService.parseBody(body)

    val visitante = VisitanteDTO(
      null,
      properties["visitante"]!!,
      properties["email_visitante"]!!,
      properties["cpf"]!!
    )
    val professor = ProfessorDTO(null, properties["professor"]!!, from)
    return VisitaSaveDTO(
      "02/10/2021",
      properties["motivo"]!!,
      professor,
      visitante
    )
  }
}