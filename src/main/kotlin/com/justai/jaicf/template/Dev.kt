package com.justai.jaicf.template

import org.springframework.boot.runApplication

/**
 * todo Document type Dev
 */
fun main(args: Array<String>) {
    System.setProperty("spring.config.location",
        "classpath:/application.yml,classpath:/dev.application.yml,optional:classpath:/local.application.yml")
    runApplication<SpringbootKotlinH2Application>(*args)
}