package com.donus.donuscodechallenge.account.controller

import com.donus.donuscodechallenge.account.controller.request.AccountRequest
import com.donus.donuscodechallenge.account.controller.request.DepositRequest
import com.donus.donuscodechallenge.account.controller.request.TakeOutRequest
import com.donus.donuscodechallenge.account.controller.request.TransferRequest
import com.donus.donuscodechallenge.account.controller.response.AccountIdResponse
import com.donus.donuscodechallenge.account.controller.response.AccountResponse
import com.donus.donuscodechallenge.transaction.TransactionType.*
import com.donus.donuscodechallenge.transaction.response.TransactionResponse
import com.donus.donuscodechallenge.account.service.impl.AccountServiceImpl
import com.donus.donuscodechallenge.transaction.repository.TransactionRepository
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/accounts")
class AccountController(
        private val accountService: AccountServiceImpl,
        private val transactionRepository: TransactionRepository
) {

    private val logger = LogManager.getLogger(this.javaClass)

    @Value("\${cashback.porcentage}")
    lateinit var cashback: String
    @Value("\${takeout.porcentage}")
    lateinit var takeout: String

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid accountRequest: AccountRequest): AccountIdResponse  {
        logger.debug("Receive request to create an account with the follow pauload-> $accountRequest")
        val accountCreated = accountService.create(accountRequest)
        return AccountIdResponse(accountCreated.id.toString())
    }

    @PostMapping
    @RequestMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    fun deposit(@RequestBody @Valid depositRequest: DepositRequest): TransactionResponse {
        logger.debug("Receive request to deposit for account with cpf-> ${depositRequest.accountId}")
        return DEPOSIT.transaction(depositRequest, cashback, accountService, transactionRepository)
    }

    @PostMapping("/take-out")
    @ResponseStatus(HttpStatus.OK)
    fun takeOut(@RequestBody @Valid takeOutRequest: TakeOutRequest): TransactionResponse {
        logger.debug("Receive request to takeout for account with cpf-> ${takeOutRequest.accountId}")
        return TAKEOUT.transaction(takeOutRequest, cashback, accountService, transactionRepository)
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    fun transfer(@RequestBody @Valid transferRequest: TransferRequest): TransactionResponse {
        return TRANSFER.transaction(transferRequest, cashback, accountService, transactionRepository)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping("/{cpf}",  produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun findByCpf(@PathVariable("cpf") cpf: String): AccountResponse? {
        return accountService.findByCpf(cpf)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun findALL() {
        accountService.findAll()
    }
}