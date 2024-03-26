package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 생성이 정상 동작한다.")
    fun saveUser() {
        // given
        val request = UserCreateRequest("동욱", null)

        // when
        userService.saveUser(request)

        // then
        val user = userRepository.findByName("동욱").orElseThrow()
        val allUsers = userRepository.findAll()
        assertThat(user).isNotNull
        assertThat(allUsers).hasSize(1)
        assertThat(user.name).isEqualTo("동욱")
        assertThat(user.age).isNull()
    }

    @Test
    @DisplayName("유저 조회가 정상 동작한다.")
    fun getUsers() {
        // given
        userRepository.saveAll(listOf(
            User("A", 20),
            User("B", null),
        ))

        // when
        val users = userService.getUsers()

        // then
        assertThat(users).hasSize(2)
        assertThat(users).extracting("name").containsExactlyInAnyOrder("A", "B")
        assertThat(users).extracting("age").containsExactlyInAnyOrder(20, null)
    }

    @Test
    @DisplayName("유저 수정이 정상 동작한다.")
    fun updateUserName() {
        // given
        val newUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(newUser.id, "B")

        // when
        userService.updateUserName(request)

        // then
        val user = userRepository.findAll()[0]
        assertThat(user.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다.")
    fun deleteUser() {
        // given
        userRepository.save(User("A", null))

        // when
        userService.deleteUser("A")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }
}