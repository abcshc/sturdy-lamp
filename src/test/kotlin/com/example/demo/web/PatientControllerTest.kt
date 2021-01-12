package com.example.demo.web

import com.example.demo.hospital.exception.HospitalNotFoundException
import com.example.demo.patient.PatientService
import com.example.demo.patient.exception.PatientNotFoundException
import com.example.demo.patient.exception.RegisterCodeOutOfBoundsException
import com.example.demo.web.exception.HttpErrorControllerAdvice
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class PatientControllerTest {
    private val patientService: PatientService = mock()
    private val patientController: PatientController = PatientController(patientService)
    private val httpErrorControllerAdvice: HttpErrorControllerAdvice = HttpErrorControllerAdvice()
    private val mockMvc: MockMvc = MockMvcBuilders
        .standaloneSetup(patientController)
        .setControllerAdvice(httpErrorControllerAdvice)
        .build()

    @AfterEach
    fun tearDown() {
        reset(patientService)
    }

    @Test
    fun test_registerPatient_success() {
        whenever(
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

    @Test
    fun test_registerPatient_throwHospitalNotFoundException() {
        whenever(
            patientService.registerPatient(
                name = "홍길동",
                gender = 'M',
                birthday = "19900101",
                phone = "01012341234",
                hospitalId = 1L
            )
        ).thenThrow(HospitalNotFoundException())

        mockMvc.perform(
            post("/patient")
                .header("X-HOSPITAL-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n  \"name\": \"홍길동\",\n  \"gender\": \"M\",\n  \"birthday\": \"19900101\",\n  \"phone\": \"01012341234\"\n}")
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun test_registerPatient_throwRegisterCodeOutOfBoundsException() {
        whenever(
            patientService.registerPatient(
                name = "홍길동",
                gender = 'M',
                birthday = "19900101",
                phone = "01012341234",
                hospitalId = 1L
            )
        ).thenThrow(RegisterCodeOutOfBoundsException())

        mockMvc.perform(
            post("/patient")
                .header("X-HOSPITAL-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n  \"name\": \"홍길동\",\n  \"gender\": \"M\",\n  \"birthday\": \"19900101\",\n  \"phone\": \"01012341234\"\n}")
        )
            .andExpect(status().isNotExtended)
    }


    @Test
    fun test_updatePatient_success() {
        mockMvc.perform(
            put("/patient/{patientId}", 1)
                .header("X-HOSPITAL-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n  \"name\": \"김길동\",\n  \"gender\": \"M\",\n  \"birthday\": \"19900101\",\n  \"phone\": \"01012341234\"\n}")
        ).andExpect(status().isNoContent)

        verify(patientService).updatePatient(1L, 1L, "김길동", 'M', "19900101", "01012341234")
    }

    @Test
    fun test_updatePatient_throwPatientNotFoundException() {
        whenever(
            patientService.updatePatient(
                hospitalId = 1L,
                patientId = 1L,
                name = "김길동",
                gender = 'M',
                birthday = "19900101",
                phone = "01012341234",
            )
        ).thenThrow(PatientNotFoundException())

        mockMvc.perform(
            put("/patient/{patientId}", 1)
                .header("X-HOSPITAL-ID", "1")
                .contentType(MediaType.APPLICATION_JSON)
                //language=json
                .content("{\n  \"name\": \"김길동\",\n  \"gender\": \"M\",\n  \"birthday\": \"19900101\",\n  \"phone\": \"01012341234\"\n}")
        )
            .andExpect(status().isNotFound)
    }


    @Test
    fun test_deletePatient_success() {
        mockMvc.perform(
            delete("/patient/{patientId}", 1)
                .header("X-HOSPITAL-ID", "1")
        ).andExpect(status().isNoContent)

        verify(patientService).deletePatient(1L, 1L)
    }


    @Test
    fun test_deletePatient_throwPatientNotFoundException() {
        whenever(
            patientService.deletePatient(
                hospitalId = 1L,
                patientId = 1L
            )
        ).thenThrow(PatientNotFoundException())

        mockMvc.perform(
            delete("/patient/{patientId}", 1)
                .header("X-HOSPITAL-ID", "1")
        ).andExpect(status().isNotFound)
    }

    // TODO: 에러 케이스 추가
}