package com.donus.donuscodechallenge.account.controller.request

import java.math.BigDecimal
import java.util.*

data class TransferRequest(
        val accountId: UUID,
        val destinationAccountId: UUID,
        val amount: BigDecimal
) : BaseTransactionRequest