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

package absaliks.gateway

import absaliks.gateway.controller.USERS_PATH
import absaliks.gateway.filter.JWTAuthenticationFilter
import absaliks.gateway.filter.JWTAuthorizationFilter
import absaliks.gateway.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

const val SECRET =
    "q2LSVgo1lVNwf6hT7vI0NOn5/qp6ZcircClOP3Ujjiu5jhQaf1tG0OFxr2tmdoH6mDuUd+r59F2CZNj1NSC4Nw=="
const val EXPIRATION_TIME = 10 * 24 * 60 * 60 * 1000 // 10 days in ms
const val TOKEN_PREFIX = "Bearer "

@Configuration
class WebSecurityConfiguration(
    val passwordEncoder: BCryptPasswordEncoder,
    val userDetailsService: UserDetailsServiceImpl
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http!!.cors().and().csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, USERS_PATH).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager()))
            .addFilter(JWTAuthorizationFilter(authenticationManager()))
            // this disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(passwordEncoder)
    }
}