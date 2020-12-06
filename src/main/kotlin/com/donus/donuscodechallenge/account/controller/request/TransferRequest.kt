package com.donus.donuscodechallenge.account.controller.request

import com.sun.istack.NotNull
import org.hibernate.validator.constraints.NotBlank
import java.math.BigDecimal
import java.util.*

data class TransferRequest(
        @field:NotNull
        val accountId: UUID,
        @field:NotNull
        val destinationAccountId: UUID,
        @field:NotNull
        val amount: BigDecimal
) : BaseTransactionRequest