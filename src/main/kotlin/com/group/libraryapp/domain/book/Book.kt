package com.group.libraryapp.domain.book

import javax.persistence.*

@Entity
class Book(
    val name: String,

    @Enumerated(EnumType.STRING)
    val type: BookType,

    // 디폴트 파라미터는 아래에 명시하는 것이 컨벤션이다.
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    // init 블록은 클래스의 초기화 블록이다.
    // 즉, 클래스가 생성자로 인스턴스화 될 때 호출된다.
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다.")
        }
    }

    // 스태틱으로 사용하기 위해 companion object 내에 선언한다.
    companion object {

        // test fixture
        fun fixture(
            name: String = "책 이름",
            type: BookType = BookType.SF,
            id: Long? = null
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id,
            )
        }

    }

}