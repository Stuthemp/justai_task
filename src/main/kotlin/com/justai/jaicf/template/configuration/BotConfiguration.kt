package com.justai.jaicf.template.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * todo Document type BotConfiguration
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "bot")
data class BotConfiguration(
    val accessToken: String
)