package com.example.demo.web

import com.example.demo.hospital.Hospital
import com.example.demo.hospital.exception.HospitalNotFoundException
import com.example.demo.patient.Patient
import com.example.demo.patient.PatientService
import com.example.demo.patient.exception.PatientNotFoundException
import com.example.demo.patient.exception.RegisterCodeOutOfBoundsException
import com.example.demo.visit.Visit
import com.example.demo.web.exception.HttpErrorControllerAdvice
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(controllers = [PatientController::class, HttpErrorControllerAdvice::class])
@AutoConfigureRestDocs
internal class PatientControllerTest {
    @MockBean
    private lateinit var patientService: PatientService

    @Autowired
    private lateinit var mockMvc: MockMvc

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
            .andDo(
                document(
                    "register-patient",
                    requestHeaders(
                        headerWithName("X-HOSPITAL-ID")
                            .description("병원 ID")
                    ),
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                        fieldWithPath("gender").type(JsonFieldType.STRING).description("성별"),
                        fieldWithPath("birthday").type(JsonFieldType.STRING).description("생년월일").optional(),
                        fieldWithPath("phone").type(JsonFieldType.STRING).description("전화번호").optional()
                    ),
                    responseFields(
                        fieldWithPath("registerCode").type(JsonFieldType.STRING).description("환자 등록 번호")
                    )
                )
            )
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


    @Test
    fun test_getPatient_success() {
        val hospital = Hospital(
            id = 1L,
            name = "우리병원",
            nursingInstitutionCode = "10000001",
            chiefName = "홍길동"
        )
        val patient = Patient(
            id = 1L,
            hospital = hospital,
            name = "홍길동",
            registerCode = "202100001",
            gender = "M",
            birthday = "19900101",
            phone = "01012341234"
        )
        patient.visits = listOf(
            Visit(1L, hospital, patient, LocalDateTime.of(2021, 1, 1, 0, 0), "3"),
            Visit(1L, hospital, patient, LocalDateTime.of(2021, 1, 2, 0, 0), "2")
        )

        whenever(
            patientService.getPatient(
                hospitalId = 1L,
                patientId = 1L
            )
        ).thenReturn(patient)

        mockMvc.perform(
            get("/patient/{patientId}", 1)
                .header("X-HOSPITAL-ID", "1")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("홍길동"))
            .andExpect(jsonPath("$.gender").value("M"))
            .andExpect(jsonPath("$.birthday").value("19900101"))
            .andExpect(jsonPath("$.phone").value("01012341234"))
            .andExpect(jsonPath("$.visits[0].id").value("1"))
            .andExpect(jsonPath("$.visits[0].visitTime").value("2021-01-01T00:00:00"))
            .andExpect(jsonPath("$.visits[0].visitStatusCode").value("3"))
            .andExpect(jsonPath("$.visits[1].id").value("1"))
            .andExpect(jsonPath("$.visits[1].visitTime").value("2021-01-02T00:00:00"))
            .andExpect(jsonPath("$.visits[1].visitStatusCode").value("2"))
    }

    @Test
    fun test_getPatient_throwPatientNotFoundException() {
        whenever(
            patientService.getPatient(
                hospitalId = 1L,
                patientId = 1L
            )
        ).thenThrow(PatientNotFoundException())

        mockMvc.perform(
            get("/patient/{patientId}", 1)
                .header("X-HOSPITAL-ID", "1")
        ).andExpect(status().isNotFound)
    }
}