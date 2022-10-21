package com.justai.jaicf.template.repository

import com.justai.jaicf.template.domain.Question
import org.springframework.data.jpa.repository.JpaRepository

/**
 * todo Document type QuestionRepository
 */
interface QuestionRepository : JpaRepository<Question, Long>