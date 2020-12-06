package com.donus.donuscodechallenge.account.service.impl

import com.donus.donuscodechallenge.account.builder.accountRequestBuild
import com.donus.donuscodechallenge.account.builder.cpf
import com.donus.donuscodechallenge.account.controller.config.ApplicationTestConfig
import com.donus.donuscodechallenge.account.service.AccountService
import com.donus.donuscodechallenge.exception.InsufficientBalanceException
import javassist.NotFoundException
import org.junit.Assert.*
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import java.math.BigDecimal
import java.util.*

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = [ApplicationTestConfig::class])
class AccountServiceImplTest{

    @Autowired
    lateinit var accountService: AccountService

    @Test
    fun `should create a account and retrieve it`(){
        val accountCreated = accountService.create(accountRequestBuild(cpf(false)))
        accountService.findByCpf(accountCreated.cpf)?.let {
            assertEquals(accountCreated.cpf, it.cpf)
        }
    }

    @Test
    fun `should throws not found account to deposit`(){
        val accountId = UUID.randomUUID()
        assertThrows(NotFoundException::class.java) {  accountService.deposit(accountId, BigDecimal(100)); }
    }

    @Test
    fun `should throws insufficient balance exception`(){
        val accountCreated = accountService.create(accountRequestBuild(cpf(false)))
        assertThrows(InsufficientBalanceException::class.java) {  accountService.takeout(accountCreated.id!!, BigDecimal(100)); }
    }
}