package com.example.demo.patient

import com.example.demo.hospital.Hospital
import com.example.demo.hospital.HospitalRepository
import com.example.demo.hospital.exception.HospitalNotFoundException
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

internal class PatientServiceImplTest {
    private val patientRepository: PatientRepository = mock()
    private val patientRegisterCodeSequenceRepository: PatientRegisterCodeSequenceRepository = mock()
    private val hospitalRepository: HospitalRepository = mock()
    private val patientService: PatientService =
        PatientServiceImpl(patientRepository, patientRegisterCodeSequenceRepository, hospitalRepository)

    @AfterEach
    fun tearDown() {
        reset(patientRepository)
        reset(patientRegisterCodeSequenceRepository)
        reset(hospitalRepository)
    }

    @Test
    fun test_registerPatient_success() {
        val hospital = Hospital(
            id = 1L,
            name = "우리병원",
            nursingInstitutionCode = "10000001",
            chiefName = "홍길동"
        )
        whenever(hospitalRepository.findById(1L)).thenReturn(Optional.of(hospital))
        whenever(patientRegisterCodeSequenceRepository.findByHospitalIdAndYear(1L, "2021")).thenReturn(
            PatientRegisterCodeSequence(1L, hospital, "2021", 2)
        )
        whenever(
            patientRepository.save(
                Patient(
                    hospital = hospital,
                    name = "홍길동",
                    registerCode = "202100002",
                    gender = "M",
                    birthday = "19900101",
                    phone = "01012341234"
                )
            )
        ).thenReturn(
            Patient(
                id = 1L,
                hospital = hospital,
                name = "홍길동",
                registerCode = "202100002",
                gender = "M",
                birthday = "19900101",
                phone = "01012341234"
            )
        )

        assertEquals("202100002", patientService.registerPatient("홍길동", 'M', "19900101", "01012341234", 1L))
    }

    @Test
    fun test_registerPatient_createSequence_success() {
        val hospital = Hospital(
            id = 1L,
            name = "우리병원",
            nursingInstitutionCode = "10000001",
            chiefName = "홍길동"
        )
        whenever(hospitalRepository.findById(1L)).thenReturn(Optional.of(hospital))
        whenever(patientRegisterCodeSequenceRepository.findByHospitalIdAndYear(1L, "2021")).thenReturn(null)
        whenever(
            patientRegisterCodeSequenceRepository.save(
                PatientRegisterCodeSequence(
                    hospital = hospital,
                    year = "2021"
                )
            )
        ).thenReturn(PatientRegisterCodeSequence(1L, hospital, "2021", 1))
        whenever(
            patientRepository.save(
                Patient(
                    hospital = hospital,
                    name = "홍길동",
                    registerCode = "202100001",
                    gender = "M",
                    birthday = "19900101",
                    phone = "01012341234"
                )
            )
        ).thenReturn(
            Patient(
                id = 1L,
                hospital = hospital,
                name = "홍길동",
                registerCode = "202100001",
                gender = "M",
                birthday = "19900101",
                phone = "01012341234"
            )
        )

        assertEquals("202100001", patientService.registerPatient("홍길동", 'M', "19900101", "01012341234", 1L))
    }

    @Test
    fun test_registerPatient_throwRegisterCodeOutOfBoundsException() {
        whenever(hospitalRepository.findById(1L)).thenReturn(Optional.empty())

        assertThrows<HospitalNotFoundException> {
            patientService.registerPatient("홍길동", 'M', "19900101", "01012341234", 1L)
        }
    }
}