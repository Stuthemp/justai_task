package com.justai.jaicf.template.configuration

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.template.scenario.MainScenario
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * todo Document type ApplicationConfiguration
 */
@Configuration
class ApplicationConfiguration(
    private val botConfiguration: BotConfiguration
) {
    @Bean
    fun botApi(mainScenario: MainScenario) = BotEngine(
        scenario = mainScenario,
        activators = arrayOf(RegexActivator, createCailaForToken(botConfiguration.accessToken))
    )

    companion object {
        private fun createCailaForToken(token: String) = CailaIntentActivator.Factory(CailaNLUSettings(token))
    }
}