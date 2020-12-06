package com.donus.donuscodechallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication
class DonusCodeChallengeApplication

fun main(args: Array<String>) {
	runApplication<DonusCodeChallengeApplication>(*args)
}
