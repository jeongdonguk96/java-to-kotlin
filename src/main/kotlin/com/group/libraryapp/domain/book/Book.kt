package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Book(
    val name: String,

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

}