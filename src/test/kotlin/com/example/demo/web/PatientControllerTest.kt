package com.example.demo.web

import com.example.demo.patient.PatientService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class PatientControllerTest {
    private val patientService: PatientService = mock()
    private val patientController: PatientController = PatientController(patientService)
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(patientController).build()

    @AfterEach
    fun tearDown() {
        reset(patientService)
    }

    @Test
    fun test_registerPatient_success() {
        Mockito.`when`(
            patientService.registerPatient(
                name = "홍길동",
                gender = 'M',
                birthday = "19900101",
                phone = "01012341234",
                hospitalId = 1L
            )
        ).thenReturn("202100001")

        mockMvc.perform(
            post("/patient")
                .header("X-HOSPITAL-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n  \"name\": \"홍길동\",\n  \"gender\": \"M\",\n  \"birthday\": \"19900101\",\n  \"phone\": \"01012341234\"\n}")
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.registerCode").value("202100001"))
    }
}