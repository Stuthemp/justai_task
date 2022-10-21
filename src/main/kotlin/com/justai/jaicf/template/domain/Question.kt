package com.justai.jaicf.template.domain

import javax.persistence.*

/**
 * todo Document type Question
 */
@Entity
@Table(name = "QUESTIONS")
class Question(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val questionId : Long,
    var firstAnswer : String,
    var secondAnswer : String,
    var thirdAnswer : String,
    var fourthAnswer : String?,
    var rightAnswer : String,
    val question: String
) {
    fun mix() {
        var answers: ArrayList<String> = ArrayList<String>()
        answers.plus(firstAnswer)
        answers.plus(secondAnswer)
        answers.plus(thirdAnswer)
        answers.plus(rightAnswer)
        answers = answers.shuffled() as ArrayList<String>
        firstAnswer = answers.removeFirst()
        secondAnswer = answers.removeFirst()
        thirdAnswer = answers.removeFirst()
        fourthAnswer = answers.removeFirst()
    }
}