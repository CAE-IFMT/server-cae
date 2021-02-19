package br.edu.ifmt.controledeacesso.api.security.utils

import br.edu.ifmt.controledeacesso.domain.entities.Usuario
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

/**
 * Created by daohn on 19/02/2021
 * @author daohn
 * @since 19/02/2021
 * @version 1.0.0
 */
@Component
class JWTUtil {

  @Value("\${jwt.secret}")
  lateinit var secret: String

  @Value("\${jwt.expiration}")
  var expiration: Long = 600000


  fun createToken(user: Usuario): String {
    return Jwts.builder()
      .setSubject(user.username)
      .setExpiration(Date(System.currentTimeMillis() + expiration))
      .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
      .compact()
  }

  fun isValidToken(token: String): Boolean {
    val claims = getClaims(token)
    if (claims != null) {
      val userName: String? = claims.subject
      val expiration: Date? = claims.expiration
      val now = Date(System.currentTimeMillis())
      return userName != null && expiration != null && now.before(expiration)
    }
    return false
  }

  private fun getClaims(token: String): Claims? {
    return Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
  }

  fun getLogin(token: String): String? {
    val claims = getClaims(token)
    return claims?.subject
  }

  fun userDetails(): UserDetails? {
    val authentication = SecurityContextHolder.getContext().authentication
    if (authentication != null && authentication.principal != null)
      return authentication.principal as UserDetails
    return null
  }
}