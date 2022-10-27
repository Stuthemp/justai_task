package com.justai.jaicf.template.scenario

import com.justai.jaicf.builder.createModel
import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.template.configuration.BotConfiguration
import com.justai.jaicf.template.domain.Question
import com.justai.jaicf.template.service.QuestionService
import org.springframework.stereotype.Component


/**
 * todo Document type MainScenario
 */
@Component
class MainScenario(
    private val botConfiguration: BotConfiguration,
    private val questionService: QuestionService
): Scenario {

    var questions : ArrayList<Question> = ArrayList()
    var currentQuestion : Question = Question(1,"","","","","","")
    var points: Int = 0

    override val model = createModel {
        state("start") {
            activators {
                regex("/start")
                intent("Hello")
            }
            action {
                questions = questionService.findQuestionsSet() as ArrayList<Question>
                points = 0
                reactions.run {
                    say(
                        "Привет, начнем тест?"
                    )
                    buttons(
                        "Начнем!"
                    )
                }
            }
        }

        state("Quiz") {
            activators {
                regex("Начнем!")
                intent("Quiz")
            }

            action {
                reactions.go("question")
            }

            state("stop_game"){
                activators{
                    regex("/stop")
                }
                action{
                    reactions.go("/end")
                }
            }

            state("question") {
                activators {
                    catchAll()
                    intent("Question")
                    regex("/question")
                }

                action {
                    if(questions.size == 0) {
                        when(points) {
                            1 -> reactions.say("Тест окончен, Вы набрали $points балл")
                            2,3,4 -> reactions.say("Тест окончен, Вы набрали $points балла")
                            else -> reactions.say("Тест окончен, Вы набрали $points баллов")
                        }
                        return@action
                    }
                    val answer = request.input
                    println("answer: " + answer + "correct: " + currentQuestion.rightAnswer)
                    if(answer == currentQuestion.rightAnswer){
                        println("Correct")
                        points++
                    }
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

        state("end") {
            activators{
            }
            action {
                context.client.clear()
                reactions.say("Тест был досрочно закончен")
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