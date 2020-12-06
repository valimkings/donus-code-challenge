package com.donus.donuscodechallenge.account.service.impl

import com.donus.donuscodechallenge.account.domain.Account
import com.donus.donuscodechallenge.account.controller.mapper.Mapper
import com.donus.donuscodechallenge.account.repository.AccountRepository
import com.donus.donuscodechallenge.account.controller.request.AccountRequest
import com.donus.donuscodechallenge.account.controller.response.AccountResponse
import com.donus.donuscodechallenge.transaction.response.TransactionResponse
import com.donus.donuscodechallenge.account.service.AccountService
import com.donus.donuscodechallenge.exception.AccountAlreadyExists
import com.donus.donuscodechallenge.exception.InsufficientBalanceException
import com.donus.donuscodechallenge.transaction.TransactionType
import javassist.NotFoundException
import org.apache.logging.log4j.LogManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class AccountServiceImpl(private val repository: AccountRepository): AccountService {

    private val logger = LogManager.getLogger(this.javaClass)

    override fun create(accountRequest: AccountRequest) : Account {
        val account = Mapper.mapper(accountRequest)
        account.let { repository.findByCpf(it.cpf).takeIf { it != null }?.let {
            throw AccountAlreadyExists("Account already exists with this CPF $it") }
        }
        return repository.save(account).also { logger.info("Account with id-> ${it.id} created with success.") }
    }

    override fun findAll() {
        repository.findAll()
    }

    override fun findByCpf(cpf: String): AccountResponse {
        return repository.findByCpf(cpf).takeIf { it != null }?.let { Mapper.mapperToResponse(it) }
                ?: throw NotFoundException("Account not found with cpf-> $cpf")
    }

    override fun deposit(accountId: UUID, amount: BigDecimal): TransactionResponse {
        val account = findById(accountId)
        val oldBalance = account.balance

        account.balance = account.balance + amount
        repository.save(account).also { logger.info("Success on deposit for account with id-> $it.id") }

        return TransactionResponse(
                accountId = account.id!!,
                amount = amount,
                balance = account.balance,
                oldBalance = oldBalance,
                type = TransactionType.DEPOSIT,
        )
    }

    override fun takeout(accountId: UUID, amount: BigDecimal): TransactionResponse {
        val account = findById(accountId)
        val oldBalance = account.balance

        account.balance = account.balance - amount

        if (account.balance.compareTo(BigDecimal.ZERO) <= -1) {
            throw InsufficientBalanceException("Insufficient balanceException")
        }
        repository.save(account).also { logger.info("Success on take out for account with id-> $it.id") }

        return TransactionResponse(
                accountId = account.id!!,
                amount = amount,
                balance = account.balance,
                oldBalance = oldBalance,
                type = TransactionType.TAKEOUT,
        )
    }

    override fun transfer(accountId: UUID, amount: BigDecimal): TransactionResponse {
        TODO("Not yet implemented")
    }

    private fun findById(accountId: UUID): Account {
        return repository.findByIdOrNull(accountId)
                ?: throw NotFoundException("Account $accountId not found")
    }
}
