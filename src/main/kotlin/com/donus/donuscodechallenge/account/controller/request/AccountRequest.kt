package com.donus.donuscodechallenge.account.controller.request

import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.br.CPF

open class AccountRequest {
    @field:NotBlank
    var name: String = ""

    @field:NotBlank
    @field:CPF
    var cpf: String = ""
}