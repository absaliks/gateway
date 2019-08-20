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

package absaliks.gateway.controller

import absaliks.gateway.model.User
import absaliks.gateway.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

const val USERS_PATH = "/users"

@RestController
@RequestMapping(USERS_PATH)
class UserController(
    val encoder: BCryptPasswordEncoder,
    val repository: UserRepository
) {
    @GetMapping
    fun getUsers(): List<User> {
        return repository.findAll()
    }

    @PostMapping
    fun createUser(@RequestBody user: User): Int {
        val existingUser = repository.findByUsername(user.username)
        val managedUser = repository.save(
            user.copy(
                id = existingUser?.id,
                password = encoder.encode(user.password)
            )
        )
        return managedUser.id ?: 0
    }
}