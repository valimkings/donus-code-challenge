package com.donus.donuscodechallenge.account.controller.mapper

import com.donus.donuscodechallenge.account.domain.Account
import com.donus.donuscodechallenge.account.controller.request.AccountRequest
import com.donus.donuscodechallenge.account.controller.response.AccountResponse
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

object Mapper {

    fun mapper(accountRequest: AccountRequest) = Account(
            id = UUID.randomUUID(),
            name = accountRequest.name,
            cpf = accountRequest.cpf,
            balance = BigDecimal.ZERO,
            createdAt = LocalDateTime.now()
    )

    fun mapperToResponse(account: Account) = AccountResponse(
            id = account.id.toString(),
            name = account.name,
            cpf = account.cpf,
            balance = account.balance,
    )
}