package com.donus.donuscodechallenge.account.repository

import com.donus.donuscodechallenge.account.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface AccountRepository : JpaRepository<Account, UUID> {

    fun findByCpf(cpf: String): Account?

}