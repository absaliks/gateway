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

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys

import org.junit.jupiter.api.Test
import java.security.Key
import java.util.*

internal class JWTAuthenticationFilterTest {

    @Test
    fun successfulAuthentication() {
        val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS512)
        println(key.encoded.size)
        println(Base64.getEncoder().encodeToString(key.encoded))
    }
}