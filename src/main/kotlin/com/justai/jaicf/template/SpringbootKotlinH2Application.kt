package com.justai.jaicf.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.justai.jaicf.template.configuration")
class SpringbootKotlinH2Application

fun main(args: Array<String>) {
	runApplication<SpringbootKotlinH2Application>(*args)
}
