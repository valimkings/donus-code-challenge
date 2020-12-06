package com.donus.donuscodechallenge.transaction.response.mapper

import com.donus.donuscodechallenge.transaction.domain.Transaction
import com.donus.donuscodechallenge.transaction.response.TransactionResponse
import java.time.LocalDateTime
import java.util.*

fun mapper(transactionResponse: TransactionResponse) =
        Transaction(
                id = UUID.randomUUID(),
                accountId = transactionResponse.accountId,
                destinationAccountId = transactionResponse.destinationAccountId,
                type = transactionResponse.type,
                amount = transactionResponse.amount,
                oldBalance = transactionResponse.oldBalance,
                balance = transactionResponse.balance,
                createdAt = LocalDateTime.now()
        )