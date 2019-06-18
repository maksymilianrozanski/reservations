package io.github.maksymilianrozanski.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RealEstateApplication

fun main(args: Array<String>) {
    runApplication<RealEstateApplication>(*args)
}
