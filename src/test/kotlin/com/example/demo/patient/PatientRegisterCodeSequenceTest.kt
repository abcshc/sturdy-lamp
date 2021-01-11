package com.example.demo.patient

import com.example.demo.hospital.Hospital
import com.example.demo.patient.exception.RegisterCodeOutOfBoundsException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class PatientRegisterCodeSequenceTest {
    @Test
    fun test_getCode_success() {
        val hospital = Hospital(
            id = 1L,
            name = "우리병원",
            nursingInstitutionCode = "10000001",
            chiefName = "홍길동"
        )
        val patientRegisterCodeSequence = PatientRegisterCodeSequence(1, hospital, "2021", 1)

        assertEquals("202100001", patientRegisterCodeSequence.getCode())
    }

    @Test
    fun test_next_success() {
        val hospital = Hospital(
            id = 1L,
            name = "우리병원",
            nursingInstitutionCode = "10000001",
            chiefName = "홍길동"
        )
        val patientRegisterCodeSequence = PatientRegisterCodeSequence(1, hospital, "2021", 1)
        patientRegisterCodeSequence.next()

        assertEquals("202100002", patientRegisterCodeSequence.getCode())
    }

    @Test
    fun test_next_throwRegisterCodeOutOfBoundsException() {
        val hospital = Hospital(
            id = 1L,
            name = "우리병원",
            nursingInstitutionCode = "10000001",
            chiefName = "홍길동"
        )
        val patientRegisterCodeSequence = PatientRegisterCodeSequence(1, hospital, "2021", 99999)
        assertEquals("202199999", patientRegisterCodeSequence.getCode())

        assertThrows<RegisterCodeOutOfBoundsException> {
            patientRegisterCodeSequence.next()
        }
    }
}