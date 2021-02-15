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
    return QRCode.from(visita.id.toString())
      .to(ImageType.JPG)
      .withCharset("UTF-8")
      .withSize(400, 400)
      .file()
  }

  fun createVisita(from: String, subject: String, body: String) {
    try {
      val dto = this.buildDTO(body, from)

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

    val response = mailBuilder()
      .to(email)
      .subject("Permissão de entrada no IFMT")
      .text(
        "Permissão de entrada para o Instituto Federal de Mato Grosso agendada " +
            "para ${visita.visitante.nome} por ${visita.professor.nome} no dia ${dateFormatted}."
      )
      .build()
      .send()
    logger.info { "Email enviado ${response.responseCode()}" }
  }

  private fun sendEmailToVisitante(visita: VisitaDTO) {
    val qrCode = this.createQRCode(visita)

    logger.info { "Email enviado para ${visita.visitante.email}" }

    val response = mailBuilder()
      .to(visita.visitante.email)
      .subject("QRCode para entrada no IFMT")
      .text("Permissão de entrada para o Instituto Federal de Mato Grosso")
      .multipart()
      .attachment(qrCode)
      .build()
      .send()
    logger.info { "Email enviado ${response.responseCode()}" }
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
      properties["data"]!!,
      properties["motivo"]!!,
      professor,
      visitante
    )
  }

  private fun mailBuilder(): MailBuilder {
    return Mail.using(mailConfiguration)
  }
}