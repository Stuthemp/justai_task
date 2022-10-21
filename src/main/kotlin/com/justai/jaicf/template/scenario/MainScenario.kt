package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.createModel
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.configuration.BotConfiguration
import org.springframework.stereotype.Component

/**
 * todo Document type MainScenario
 */
@Component
class MainScenario(
    private val botConfiguration: BotConfiguration
): Scenario {

    override val model = createModel {
        state("start") {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                reactions.run {
                    sayRandom(
                        "Hello! How can I help?",
                        "Hi there! How can I help you?"
                    )
                    buttons(
                        "Help me!",
                        "How are you?",
                        "What is your name?"
                    )
                }
            }
        }

        state("bye") {
            activators {
                intent("Bye")
            }

            action {
                reactions.sayRandom(
                    "See you soon!",
                    "Bye-bye!"
                )
            }
        }

        state("smalltalk", noContext = true) {
            activators {
                anyIntent()
            }

            action(caila) {
                activator.topIntent.answer?.let { reactions.say(it) } ?: reactions.go("/fallback")
            }
        }

        fallback {
            reactions.sayRandom(
                "Sorry, I didn't get that...",
                "Sorry, could you repeat please?"
            )
        }
    }
}