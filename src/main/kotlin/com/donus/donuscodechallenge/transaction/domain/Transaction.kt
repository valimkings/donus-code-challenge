package com.donus.donuscodechallenge.transaction.domain

import com.donus.donuscodechallenge.transaction.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Transaction(
        @Id
        @GeneratedValue(generator = "uuid2")
        var id: UUID? = null,
        var accountId: UUID,
        var destinationAccountId: UUID? = null,
        @Enumerated(EnumType.STRING)
        @Column(length = 15)
        var type: TransactionType,
        var amount: BigDecimal,
        var oldBalance: BigDecimal,
        var balance: BigDecimal,
        var createdAt: LocalDateTime?
)