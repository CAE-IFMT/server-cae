package br.edu.ifmt.controledeacesso.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

/**
 * Created by daohn on 10/02/2021
 * @author daohn
 * @since 10/02/2021
 * @version 1.0.0
 */
@SpringBootTest
class ModelMapperTest {

  @Autowired
  private lateinit var mapper: ModelMapper

  @Test
  fun stringToLocalDateTime() {
    val data = "11/02/2020 15:50"
    val localDateTime = mapper.map(data, LocalDateTime::class.java)
    assertEquals(11, localDateTime.dayOfMonth)
    assertEquals(2, localDateTime.monthValue)
    assertEquals(2020, localDateTime.year)
    assertEquals(15, localDateTime.hour)
    assertEquals(50, localDateTime.minute)
  }

  @Test
  fun localDateTimeToStringTest() {
    val data = LocalDateTime.of(2020, 2, 11, 15, 50)
    val dataString = mapper.map(data, String::class.java)
    assertEquals("11/02/2020 15:50", dataString)
  }
}