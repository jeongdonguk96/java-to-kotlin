package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val bookRepository: BookRepository,
    private val bookService: BookService,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("책 등록이 정상 동작한다")
    fun saveBook() {
        // given
        val newBook = BookRequest("삼체")

        // when
        bookService.saveBook(newBook)

        // then
        val allBooks = bookRepository.findAll()
        assertThat(allBooks).hasSize(1)
        assertThat(allBooks[0].name).isEqualTo("삼체")
    }

    @Test
    @DisplayName("책 대출이 정상 동작한다")
    fun loanBook() {
        // given
        bookRepository.save(Book("삼체"))
        userRepository.save(User("동욱", null))
        val newBookLoanRequest = BookLoanRequest("동욱", "삼체")

        // when
        bookService.loanBook(newBookLoanRequest)

        // then
        val allLoanHistory = userLoanHistoryRepository.findAll()
        assertThat(allLoanHistory).hasSize(1)
        assertThat(allLoanHistory[0].bookName).isEqualTo("삼체")
        assertThat(allLoanHistory[0].user.name).isEqualTo("동욱")
        assertThat(allLoanHistory[0].isReturn).isFalse()
    }

    @Test
    @DisplayName("반납되지 않은 책은 대출되지 않는다")
    fun loanBookWithException() {
        // given
        bookRepository.save(Book("삼체"))
        val newUser = userRepository.save(User("동욱", null))
        userLoanHistoryRepository.save(
            UserLoanHistory(
                newUser,
                "삼체",
                false,
                null
            )
        )
        val newBookLoanRequest = BookLoanRequest("동욱", "삼체")

        // when & then
        val message = assertThrows<IllegalArgumentException> {
            bookService.loanBook(newBookLoanRequest)
        }.message

        assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
    }

    @Test
    @DisplayName("책 반납이 정상 동작한다")
    fun returnBook() {
        // given
        bookRepository.save(Book("삼체"))
        val newUser = userRepository.save(User("동욱", null))
        userLoanHistoryRepository.save(
            UserLoanHistory(
                newUser,
                "삼체",
                false,
                null
            )
        )
        val newBookReturnRequest = BookReturnRequest("동욱", "삼체")

        // when
        bookService.returnBook(newBookReturnRequest)

        // then
        val allLoanHistories = userLoanHistoryRepository.findAll()
        assertThat(allLoanHistories).hasSize(1)
        assertThat(allLoanHistories[0].isReturn).isTrue()
    }

}