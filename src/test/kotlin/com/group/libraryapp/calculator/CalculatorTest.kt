package com.group.libraryapp.calculator

import org.assertj.core.api.AssertionsForInterfaceTypes.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.assertThrows

class CalculatorTest {
    
    companion object {
        
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            println("전체 테스트 전")
        }

        @JvmStatic
        @AfterAll
        fun afterAll() {
            println("전체 테스트 후")
        }
    }

    @Test
    fun add() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.add(3)

        // then
        assertThat(calculator.number).isEqualTo(8)
    }

    @Test
    fun minus() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(3)

        // then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun multiply() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        assertThat(calculator.number).isEqualTo(15)
    }

    @Test
    fun divide() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(2)

        // then
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideWith0() {
        // given
        val calculator = Calculator(5)

        // when
        val message = assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.message

        // then
        assertThat(message).isEqualTo("0으로 나눌 수 없습니다.")
    }

}