package com.example.demo

import com.example.demo.configuration.TestQuerydslConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@DataJpaTest
@Import(TestQuerydslConfiguration::class)
annotation class RepositoryTest