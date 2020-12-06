package com.donus.donuscodechallenge.transaction

import com.donus.donuscodechallenge.account.controller.request.BaseTransactionRequest
import com.donus.donuscodechallenge.account.controller.request.DepositRequest
import com.donus.donuscodechallenge.account.controller.request.TakeOutRequest
import com.donus.donuscodechallenge.account.controller.request.TransferRequest
import com.donus.donuscodechallenge.transaction.response.TransactionResponse
import com.donus.donuscodechallenge.account.service.impl.AccountServiceImpl
import com.donus.donuscodechallenge.transaction.repository.TransactionRepository
import com.donus.donuscodechallenge.transaction.response.mapper.mapper
import org.apache.logging.log4j.LogManager
import java.math.BigDecimal
import java.math.BigDecimal.*
import java.math.RoundingMode


enum class TransactionType {

    DEPOSIT {
        override fun transaction(
                request: BaseTransactionRequest,
                percentage: String,
                accountService: AccountServiceImpl,
                transactionRepository: TransactionRepository
        ): TransactionResponse {
            val depositRequest = request as DepositRequest
            val transactionResponse = accountService.deposit(depositRequest.accountId, depositRequest.amount.multiply(ONE.plus(BigDecimal(percentage))).setScale(2, RoundingMode.HALF_UP))
            transactionRepository.save(mapper(transactionResponse)).also {
                logger.info("Success on save transation with id-> $it")
            }
            return transactionResponse
        }
    },
    TAKEOUT {
        override fun transaction(
                request: BaseTransactionRequest,
                percentage: String,
                accountService: AccountServiceImpl,
                transactionRepository: TransactionRepository
        ): TransactionResponse {
            val takeOutRequest = request as TakeOutRequest
            val transactionResponse = accountService.takeout(takeOutRequest.accountId, takeOutRequest.amount.plus(takeOutRequest.amount.multiply(BigDecimal(percentage))).setScale(2, RoundingMode.HALF_UP))
            transactionRepository.save(mapper(transactionResponse)).also {
                logger.info("Success on save transation with id-> $it")
            }
            return transactionResponse
        }
    },
    TRANSFER {
        override fun transaction(
                request: BaseTransactionRequest,
                percentage: String,
                accountService: AccountServiceImpl,
                transactionRepository: TransactionRepository
        ): TransactionResponse {
            val transferRequest = request as TransferRequest
            val transactionResponse = accountService.takeout(transferRequest.accountId, transferRequest.amount)
            accountService.deposit(transferRequest.destinationAccountId, transferRequest.amount)
            transactionResponse.destinationAccountId = transferRequest.destinationAccountId
            transactionResponse.type = TRANSFER
            transactionRepository.save(mapper(transactionResponse)).also {
                logger.info("Success on save transation with id-> $it")
            }
            return transactionResponse
        }
    };

    val logger = LogManager.getLogger(this.javaClass)

    abstract fun transaction(
            request: BaseTransactionRequest,
            percentage: String,
            accountService: AccountServiceImpl,
            transactionRepository: TransactionRepository
    ): TransactionResponse
}