package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryAppApplication

// 최상위 함수
fun main(args: Array<String>) {
    runApplication<LibraryAppApplication>(*args)
}