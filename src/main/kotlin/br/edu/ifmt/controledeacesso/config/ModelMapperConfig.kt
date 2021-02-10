package br.edu.ifmt.controledeacesso.config

import org.modelmapper.AbstractConverter
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.DateTimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException


/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Configuration
class ModelMapperConfig {

  private var diaMesAnoHoraMinFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")


  @Bean
  fun modelMapper(): ModelMapper {
    val mapper = ModelMapper()
    mapper.configuration.matchingStrategy = MatchingStrategies.STANDARD

    mapper.addConverter(stringToLocalDateTimeConverter())
    mapper.addConverter(localDateTimeToStringConverter())

    return mapper
  }

  private fun localDateTimeToStringConverter(): AbstractConverter<LocalDateTime, String> {
    return object : AbstractConverter<LocalDateTime, String>() {
      override fun convert(src: LocalDateTime?): String? {
        try {
          if (src == null)
            return null
          return diaMesAnoHoraMinFormatter.format(src)
        } catch (e: DateTimeException) {
          println(e.message)
          return null
        }
      }
    }
  }

  private fun stringToLocalDateTimeConverter(): AbstractConverter<String, LocalDateTime> {
    return object : AbstractConverter<String, LocalDateTime>() {
      override fun convert(src: String?): LocalDateTime? {
        try {
          if (src == null) {
            return null
          }
          return LocalDateTime.parse(src, diaMesAnoHoraMinFormatter)
        } catch (e: DateTimeParseException) {
          println(e.message)
          return null
        }
      }
    }
  }
}