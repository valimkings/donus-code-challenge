package com.donus.donuscodechallenge.account.service

import com.donus.donuscodechallenge.account.domain.Account
import com.donus.donuscodechallenge.account.controller.request.AccountRequest
import com.donus.donuscodechallenge.account.controller.request.DepositRequest
import com.donus.donuscodechallenge.account.controller.request.TakeOutRequest
import com.donus.donuscodechallenge.account.controller.request.TransferRequest
import com.donus.donuscodechallenge.account.controller.response.AccountResponse
import com.donus.donuscodechallenge.transaction.response.TransactionResponse
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transaction

interface AccountService {
    fun create(accountRequest: AccountRequest) : Account
    fun findAll()
    fun findByCpf(cpf: String): AccountResponse?
    fun deposit(accountId: UUID, amount: BigDecimal): TransactionResponse
    fun takeout(accountId: UUID, amount: BigDecimal): TransactionResponse
    fun transfer(accountId: UUID, amount: BigDecimal): TransactionResponse
}