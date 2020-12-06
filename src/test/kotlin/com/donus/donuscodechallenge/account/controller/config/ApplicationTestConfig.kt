package com.donus.donuscodechallenge.account.controller.config

import com.donus.donuscodechallenge.DonusCodeChallengeApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(DonusCodeChallengeApplication::class)
class ApplicationTestConfig
