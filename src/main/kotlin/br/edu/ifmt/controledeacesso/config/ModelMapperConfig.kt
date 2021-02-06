package br.edu.ifmt.controledeacesso.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @project cae-api
 * @author daohn on 06/02/2021
 */
@Configuration
class ModelMapperConfig {
  @Bean
  fun modelMapper(): ModelMapper {
    val modelMapper = ModelMapper()
    modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
    return modelMapper
  }
}