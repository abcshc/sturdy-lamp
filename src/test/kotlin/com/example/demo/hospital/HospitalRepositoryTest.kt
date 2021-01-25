package com.example.demo.hospital

import com.example.demo.RepositoryTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
internal class HospitalRepositoryTest {
    @Autowired
    lateinit var hospitalRepository: HospitalRepository

    @Test
    fun test_saveHospital_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")

        assertEquals(1, hospital.id)
        assertEquals("우리병원", hospital.name)
        assertEquals("10000001", hospital.nursingInstitutionCode)
        assertEquals("홍길동", hospital.chiefName)
    }
}