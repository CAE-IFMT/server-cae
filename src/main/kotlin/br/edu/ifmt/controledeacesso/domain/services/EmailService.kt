package br.edu.ifmt.controledeacesso.domain.services

import br.edu.ifmt.controledeacesso.domain.dto.VisitaDTO
import mu.KotlinLogging
import net.glxn.qrgen.core.image.ImageType
import net.glxn.qrgen.javase.QRCode
import net.sargue.mailgun.Configuration
import net.sargue.mailgun.Mail
import net.sargue.mailgun.MailBuilder
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.io.File


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
  private val mailConfiguration: Configuration,
  private val mapper: ModelMapper
) {
  private val logger = KotlinLogging.logger { }

  private fun createQRCode(visita: VisitaDTO): File {
    return QRCode.from(visita.toJson())
      .to(ImageType.JPG)
      .withCharset("UTF-8")
      .withSize(300, 300)
      .file()
  }

  fun createVisita(from: String, subject: String, body: String) {
    try {
      val dto = this.parserService.buildDTO(body, from)

      logger.info { "DTO criado com sucesso" }

      val visita = visitaService.save(dto)

      logger.info { "Visita persistida com sucesso" }

      sendEmailToProfessor(from, visita)
      sendEmailToVisitante(visita)

    } catch (exception: Exception) {
      print(exception.message)
    }
  }

  private fun sendEmailToProfessor(from: String, visita: VisitaDTO) {
    val email = this.parserService.extractEmail(from)
    logger.info { "Enviando email para $email" }

    val dateFormatted = mapper.map(visita.data, String::class.java)

    mailBuilder()
      .to(email)
      .subject("Permissão de entrada no IFMT")
      .text(
        "Permissão de entrada para o Instituto Federal de Mato Grosso agendada " +
            "para ${visita.visitante.nome} por ${visita.professor.nome} no dia ${dateFormatted}."
      )
      .build()
      .send()
    logger.info { "Email enviado com sucesso!" }
  }

  private fun sendEmailToVisitante(visita: VisitaDTO) {
    val qrCode = this.createQRCode(visita)

    logger.info { "Email enviado para ${visita.visitante.email}" }

    mailBuilder()
      .to(visita.visitante.email)
      .subject("QRCode para entrada no IFMT")
      .text("Permissão de entrada para o Instituto Federal de Mato Grosso")
      .multipart()
      .attachment(qrCode)
      .build()
      .send()
    logger.info { "Email enviado com sucesso!" }
  }

  private fun mailBuilder(): MailBuilder {
    return Mail.using(mailConfiguration)
  }
}