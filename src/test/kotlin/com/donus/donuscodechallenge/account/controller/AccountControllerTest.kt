package com.donus.donuscodechallenge.account.controller

import com.donus.donuscodechallenge.account.builder.*
import com.donus.donuscodechallenge.account.builder.takeoutRequestBuilder
import com.donus.donuscodechallenge.account.controller.config.ApplicationTestConfig
import com.donus.donuscodechallenge.account.repository.AccountRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.math.BigDecimal

@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ApplicationTestConfig::class])
class AccountControllerTest{

    @Autowired
    lateinit var context: WebApplicationContext

    @Autowired
    lateinit var accountRepository: AccountRepository

    private lateinit var mockMvc: MockMvc


    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build()
    }

    @org.junit.Test
    fun `should create an account`() {
        createAccount(cpf(false))
    }

    @org.junit.Test
    fun `when deposit of 1000 reais to an account without amount return it with cashback`() {
        val cpf = cpf(false)
        createAccount(cpf)
        val findByCpf = accountRepository.findByCpf(cpf)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/deposit")
                .content(asJsonString(depositRequestBuilder(findByCpf!!.id!!, BigDecimal(1000))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.amount").value("1005.0"))

    }

    @org.junit.Test
    fun `when lockout from an account with 1005 reais it returns with discounts`() {
        val cpf = cpf(false)
        createAccount(cpf)
        val findByCpf = accountRepository.findByCpf(cpf)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/deposit")
                .content(asJsonString(depositRequestBuilder(findByCpf!!.id!!, BigDecimal(1000))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.amount").value("1005.0"))

    }

    @org.junit.Test
    fun `when lockout 500 reais from an account with 1005 reais it returns 502_50 with discounts`() {
        val cpf = cpf(false)
        createAccount(cpf)
        val accountFetched = accountRepository.findByCpf(cpf)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/deposit")
                .content(asJsonString(depositRequestBuilder(accountFetched!!.id!!, BigDecimal(1000))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.amount").value("1005.0"))

        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/take-out")
                .content(asJsonString(takeoutRequestBuilder(accountFetched.id!!, BigDecimal(500))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.oldBalance").value("1005.0"))
                .andExpect(jsonPath("$.amount").value("502.5"))

    }

    @org.junit.Test
    fun `should deposit 200 reais and transfer 200 to other account with 0 reais and remaining only cashback`() {
        val cpfAccountFrom = cpf(false)
        createAccount(cpfAccountFrom)

        val accountFetched = accountRepository.findByCpf(cpfAccountFrom)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/deposit")
                .content(asJsonString(depositRequestBuilder(accountFetched!!.id!!, BigDecimal(200))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.amount").value("201.0"))

        val cpfAccountTo = cpf(false)
        createAccount(cpfAccountTo)
        val accountFetchedTo = accountRepository.findByCpf(cpfAccountTo)

        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts/transfer")
                .content(asJsonString(transferRequestBuilder(accountFetched.id!!, accountFetchedTo!!.id!!, BigDecimal(200))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.oldBalance").value("201.0"))
                .andExpect(jsonPath("$.balance").value("1.0"))

    }

    private fun createAccount(cpf: String) {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/accounts")
                .content(asJsonString(accountRequestBuild(cpf)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated)
    }

    fun asJsonString(obj: Any?): String {
        return try {
            ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}