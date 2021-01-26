package com.example.demo

import com.example.demo.configuration.TestQuerydslConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestConstructor

@Target(AnnotationTarget.CLASS)
@DataJpaTest
@Import(TestQuerydslConfiguration::class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
annotation class CustomDataJpaTest