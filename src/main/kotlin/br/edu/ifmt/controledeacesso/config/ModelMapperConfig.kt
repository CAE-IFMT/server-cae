package br.edu.ifmt.controledeacesso.config

import br.edu.ifmt.controledeacesso.models.dto.VisitaSaveDTO
import br.edu.ifmt.controledeacesso.models.entities.Visita
import org.modelmapper.AbstractConverter
import org.modelmapper.ModelMapper
import org.modelmapper.PropertyMap
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter




/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Configuration
class ModelMapperConfig {

  private var diaMesAnoHoraMinFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")


  @Bean
  fun modelMapper(): ModelMapper {
    val mapper = ModelMapper()
    mapper.configuration.matchingStrategy = MatchingStrategies.STRICT


    mapper.addMappings(object : PropertyMap<VisitaSaveDTO, Visita>() {
      override fun configure() {
        skip(destination.data)
      }
    })

    mapper.addConverter(stringToLocalDateTimeConverter())
    mapper.addConverter(localDateTimeToStringConverter())

    return mapper
  }

  private fun localDateTimeToStringConverter(): AbstractConverter<LocalDateTime, String> {
    return object : AbstractConverter<LocalDateTime, String>() {
      override fun convert(src: LocalDateTime?): String? {
        if (src == null) {
          return null
        }
        return diaMesAnoHoraMinFormatter.format(src)
      }
    }
  }

  private fun stringToLocalDateTimeConverter(): AbstractConverter<String, LocalDateTime> {
    return object : AbstractConverter<String, LocalDateTime>() {
      override fun convert(src: String?): LocalDateTime? {
        if (src == null) {
          return null
        }
        return LocalDateTime.parse(src, diaMesAnoHoraMinFormatter)
      }
    }
  }
}