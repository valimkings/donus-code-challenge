package com.donus.donuscodechallenge.account.controller.response

import java.math.BigDecimal

class AccountResponse(
        val id: String,
        val name: String,
        val cpf: String,
        val balance: BigDecimal
)