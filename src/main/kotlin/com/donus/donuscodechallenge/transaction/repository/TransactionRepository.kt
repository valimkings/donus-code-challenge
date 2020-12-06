package com.donus.donuscodechallenge.transaction.repository

import com.donus.donuscodechallenge.transaction.domain.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository : JpaRepository<Transaction, UUID> {

}