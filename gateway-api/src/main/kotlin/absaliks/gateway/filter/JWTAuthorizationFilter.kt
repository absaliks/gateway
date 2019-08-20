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

import absaliks.gateway.SECRET
import absaliks.gateway.TOKEN_PREFIX
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.security.Key
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain
    ) {
        val header = request.getHeader(AUTHORIZATION)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        val authentication = getAuthentication(request)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val authString = request.getHeader(AUTHORIZATION) ?: return null
        val key: Key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET))
        val token = authString.replace(TOKEN_PREFIX, "")
        val claims = Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)

        if (claims.body.subject != null) {
            return UsernamePasswordAuthenticationToken(
                claims.body.subject,
                null,
                ArrayList()
            )
        }
        return null
    }
}