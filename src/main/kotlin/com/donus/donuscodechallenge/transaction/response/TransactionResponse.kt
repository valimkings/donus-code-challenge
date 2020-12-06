package com.donus.donuscodechallenge.transaction.response


import com.donus.donuscodechallenge.transaction.TransactionType
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionResponse(
        val id: UUID? = null,
        val accountId: UUID,
        var destinationAccountId: UUID? = null,
        var type: TransactionType,
        val amount: BigDecimal,
        val oldBalance: BigDecimal,
        val balance: BigDecimal,
        val createdAt: Instant? = null
)