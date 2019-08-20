/*
 * Copyright (C) 2019  Shamil Absalikov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package absaliks.gateway.filter

import absaliks.gateway.EXPIRATION_TIME
import absaliks.gateway.SECRET
import absaliks.gateway.TOKEN_PREFIX
import absaliks.gateway.model.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.security.Key
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(private val authManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?
    ): Authentication {
        try {
            val user: User = ObjectMapper().readValue(request.inputStream)

            return authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    user.username,
                    user.password,
                    ArrayList<GrantedAuthority>()
                )
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        auth: Authentication
    ) {
        val user = auth.principal as org.springframework.security.core.userdetails.User
        val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET))
        val token = Jwts.builder()
            .setSubject(user.username)
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact()
        response.addHeader(AUTHORIZATION, "$TOKEN_PREFIX$token")
    }
}