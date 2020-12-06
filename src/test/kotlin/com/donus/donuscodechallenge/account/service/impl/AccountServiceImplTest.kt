package com.donus.donuscodechallenge.account.service.impl

import com.donus.donuscodechallenge.account.builder.accountRequestBuild
import com.donus.donuscodechallenge.account.builder.cpf
import com.donus.donuscodechallenge.account.controller.config.ApplicationTestConfig
import com.donus.donuscodechallenge.account.service.AccountService
import org.junit.Assert.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration

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
}