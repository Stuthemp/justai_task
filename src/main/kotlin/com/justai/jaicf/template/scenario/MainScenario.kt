package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.createModel
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.configuration.BotConfiguration
import com.justai.jaicf.template.domain.Question
import com.justai.jaicf.template.repository.QuestionRepository
import org.springframework.stereotype.Component

/**
 * todo Document type MainScenario
 */
@Component
class MainScenario(
    private val botConfiguration: BotConfiguration,
    private val questionRepository: QuestionRepository
): Scenario {

    var questions : ArrayList<Question> = ArrayList()
    var currentQuestion : Question = Question(1,"","","","","","")


    override val model = createModel {
        state("start") {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                questions = questionRepository.findAll() as ArrayList<Question>
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

        state("question") {
            activators {
                intent("Question")
                regex("/question")
            }

            action {
                currentQuestion = questions.removeFirst()
                currentQuestion.mix()
                reactions.run {
                    say(currentQuestion.question)
                    currentQuestion.fourthAnswer?.let {
                        buttons(
                            currentQuestion.firstAnswer,
                            currentQuestion.secondAnswer,
                            currentQuestion.thirdAnswer,
                            it
                        )
                    }
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