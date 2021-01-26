package com.example.demo.hospital

import com.example.demo.CustomDataJpaTest
import com.example.demo.inputTestData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@CustomDataJpaTest
internal class HospitalCustomDataJpaTest(private val hospitalRepository: HospitalRepository) {

    @Test
    fun test_saveHospital_through_inputTestData_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")

        assertEquals(1, hospital.id)
        assertEquals("우리병원", hospital.name)
        assertEquals("10000001", hospital.nursingInstitutionCode)
        assertEquals("홍길동", hospital.chiefName)
    }
}