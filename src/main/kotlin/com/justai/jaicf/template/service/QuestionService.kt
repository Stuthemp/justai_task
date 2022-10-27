package com.justai.jaicf.template.service

import com.justai.jaicf.template.domain.Question
import com.justai.jaicf.template.repository.QuestionRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.security.SecureRandom

/**
 * В задании точно не сказано, сколько всего должно быть вопросов, и как они должны выбираться, я оставлю
 * несколько способов
 */
@Service
class QuestionService(private val questionRepository: QuestionRepository) {

    /**
     * Способ для "статичного" теста, если есть всего 20 вопросов и они всегда одни и те же
     */
    fun findQuestionsSet() : List<Question> {
        return questionRepository.findAll();
    }

    /**
     * Способ для получения вопросов страницами, тогда число вопросов в базе должно делиться на 20,
     * и в функции которая будет вызывать эту, должен быть способ выбрать страницу
     */
    fun findQuestionsPage(pageable: Pageable) : List<Question> {
        return questionRepository.findAll(pageable).content
    }

    /**
     * Этот метод будет работать с любым колчисетвом вопросов в базе, главное чтобы их было >= 20
     * Разумеется, плохо если в таблице огромное количество записей, которые мы тянем
     */
    fun findAllAndTakeTwenty() : List<Question> {
        val questions = questionRepository.findAll()
        val result = ArrayList<Question>()

        val rand: SecureRandom = SecureRandom()
        for (i in 1..20){
            result.add(questions.removeAt(rand.nextInt(questions.size)))
        }
        return result
    }

}