package com.donus.donuscodechallenge.account.builder

import com.donus.donuscodechallenge.account.controller.request.*
import jdk.jfr.DataAmount
import java.math.BigDecimal
import java.util.*

fun accountRequestBuild(cpf: String): AccountRequest {
    return AccountRequest().also {
        it.name = "Full Name"
        it.cpf = cpf
    }
}

fun depositRequestBuilder(accountId: UUID, amount: BigDecimal) = DepositRequest(accountId = accountId, amount = amount)
fun takeoutRequestBuilder(accountId: UUID, amount: BigDecimal) = TakeOutRequest(accountId = accountId, amount = amount)
fun transferRequestBuilder(accountId: UUID, destinationAccountId: UUID, amount: BigDecimal) = TransferRequest(
        accountId = accountId,
        destinationAccountId = destinationAccountId,
        amount = amount
)