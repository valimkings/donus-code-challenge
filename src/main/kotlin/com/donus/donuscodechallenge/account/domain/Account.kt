package com.donus.donuscodechallenge.account.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Account(
        @Id
        @GeneratedValue(generator = "uuid2")
        var id: UUID? = null,
        var name: String,
        var cpf: String,
        var balance: BigDecimal,

        @CreatedDate
        var createdAt: LocalDateTime?,

)