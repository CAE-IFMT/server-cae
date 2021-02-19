package br.edu.ifmt.controledeacesso.api.security.utils

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.io.IOException
import javax.servlet.http.HttpServletResponse


/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@Throws(IOException::class)
fun write(response: HttpServletResponse, status: HttpStatus, json: String?) {
  response.status = status.value()
  response.characterEncoding = "UTF-8"
  response.contentType = MediaType.APPLICATION_JSON_VALUE
  json?.let {
    response.writer.write(json)
  }
}