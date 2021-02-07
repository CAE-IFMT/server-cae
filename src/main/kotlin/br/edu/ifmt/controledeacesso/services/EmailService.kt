package br.edu.ifmt.controledeacesso.services

import br.edu.ifmt.controledeacesso.models.dto.ProfessorDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitaDTO
import br.edu.ifmt.controledeacesso.models.dto.VisitanteDTO
import net.glxn.qrgen.core.image.ImageType
import net.glxn.qrgen.javase.QRCode
import net.sargue.mailgun.MailBuilder
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

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
  private val builder: MailBuilder,
) {

  private fun createQRCode(): File {
    return QRCode.from(UUID.randomUUID().toString())
      .to(ImageType.JPG)
      .withCharset("UTF-8")
      .withSize(400, 400)
      .file()
  }

  private fun sendEmail(email: String) {
    val qrcode = createQRCode()

    builder
      .to(email)
      .subject("QRCode de autenticação")
      .text("Permissão de entrada para o Instituto Federal de Mato Grosso")
      .multipart()
      .attachment(qrcode)
      .build()
      .send()
  }

  fun createVisita(from: String, subject: String, body: String) {
    try {
      val properties = parserService.parseBody(body)
      val data = parserService.parseDate(properties)

      val visitante = VisitanteDTO(
        null,
        properties["visitante"]!!,
        properties["email_visitante"]!!,
        properties["cpf"]!!
      )

      val professor = ProfessorDTO(null, properties["professor"]!!, from)
      val visita = VisitaDTO(null, data, properties["motivo"]!!, false, professor, visitante)

      visitaService.save(visita)
    } catch (exception: Exception) {

    } finally {

    }

  }
}