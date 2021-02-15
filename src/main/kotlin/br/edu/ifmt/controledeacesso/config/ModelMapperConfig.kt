package br.edu.ifmt.controledeacesso.config

import org.modelmapper.AbstractConverter
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.DateTimeException
import java.time.LocalDate
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

  private fun localDateTimeToStringConverter(): AbstractConverter<LocalDate, String> {
    return object : AbstractConverter<LocalDate, String>() {
      override fun convert(src: LocalDate?): String? {
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

  private fun stringToLocalDateTimeConverter(): AbstractConverter<String, LocalDate> {
    return object : AbstractConverter<String, LocalDate>() {
      override fun convert(src: String?): LocalDate? {
        try {
          if (src == null) {
            return null
          }
          return LocalDate.parse(src, diaMesAnoHoraMinFormatter)
        } catch (e: DateTimeParseException) {
          println(e.message)
          return null
        }
      }
    }
  }
}