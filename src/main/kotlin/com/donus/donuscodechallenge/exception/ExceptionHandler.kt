package com.donus.donuscodechallenge.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(AccountAlreadyExists::class)
    fun handleConflictException(e: AccountAlreadyExists): HttpStatus {
        throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.message, e)
    }
}