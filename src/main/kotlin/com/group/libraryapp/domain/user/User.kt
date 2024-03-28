package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import javax.persistence.*

@Entity
class User(
    var name: String,
    val age: Int?,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val loanHistories: MutableList<UserLoanHistory> = mutableListOf(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다")
        }
    }

    fun updateName(name: String) {
        this.name = name
    }

    fun loanBook(book: Book) {
        this.loanHistories.add(UserLoanHistory(this, book.name, UserLoanStatus.LOANED))
    }

    fun returnBook(bookName: String) {
        this.loanHistories
            .first { history -> history.bookName == bookName }
            .doReturn()
    }

}