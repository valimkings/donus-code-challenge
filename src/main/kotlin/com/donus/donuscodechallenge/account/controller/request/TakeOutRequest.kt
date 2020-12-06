package com.donus.donuscodechallenge.account.controller.request

import java.math.BigDecimal
import java.util.*

data class TakeOutRequest(
    val accountId: UUID,
    val amount: BigDecimal
) : BaseTransactionRequest