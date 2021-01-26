package com.example.demo.patient

import com.example.demo.CustomDataJpaTest
import com.example.demo.flushAndClear
import com.example.demo.hospital.HospitalRepository
import com.example.demo.inputTestData
import com.example.demo.visit.VisitRepository
import com.example.demo.visit.VisitStatus
import com.example.demo.web.request.PatientSearchType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@CustomDataJpaTest
internal class PatientRepositoryTest(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val patientRepository: PatientRepository,
    private val hospitalRepository: HospitalRepository,
    private val visitRepository: VisitRepository
) {

    @Test
    fun test_savePatient_through_inputTestData_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        val patient = patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")

        assertEquals("김길동", patient.name)
        assertEquals("202100001", patient.registerCode)
        assertEquals("M", patient.gender)
        assertEquals("19900101", patient.birthday)
        assertEquals("01012341234", patient.phone)
    }

    @Test
    fun test_findByHospitalIdAndIdAndDeletedFalse_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        val patient = patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")

        val result: Patient = patientRepository.findByHospitalIdAndIdAndDeletedFalse(hospital.id!!, patient.id!!)!!

        assertEquals(patient.id, result.id)
        assertEquals("김길동", result.name)
        assertEquals("202100001", result.registerCode)
        assertEquals("M", result.gender)
        assertEquals("19900101", result.birthday)
        assertEquals("01012341234", result.phone)
    }

    @Test
    fun test_findByHospitalIdAndIdAndDeletedFalse_when_deleted_return_empty() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        val patient = patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")
        patient.delete()
        patientRepository.save(patient)

        val result: Patient? = patientRepository.findByHospitalIdAndIdAndDeletedFalse(hospital.id!!, patient.id!!)

        assertNull(result)
    }

    @Test
    fun test_findForDetails_return_contains_sorted_visits() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        val patient = patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")
        visitRepository.inputTestData(hospital, patient, LocalDateTime.of(2021, 1, 1, 0, 0, 0), VisitStatus.END)
        visitRepository.inputTestData(hospital, patient, LocalDateTime.of(2021, 1, 2, 0, 0, 0), VisitStatus.CANCEL)
        visitRepository.inputTestData(hospital, patient, LocalDateTime.of(2021, 1, 3, 0, 0, 0), VisitStatus.VISITING)
        entityManager.flushAndClear()

        val result: Patient = patientRepository.findForDetails(hospital.id!!, patient.id!!)!!

        assertEquals(3, result.visits.size)
        assertEquals(VisitStatus.END.code, result.visits[2].visitStatusCode)
        assertEquals(VisitStatus.CANCEL.code, result.visits[1].visitStatusCode)
        assertEquals(VisitStatus.VISITING.code, result.visits[0].visitStatusCode)
    }

    @Test
    fun test_searchPatient_search_by_name_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        val kim = patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")
        val lee = patientRepository.inputTestData(hospital, "이길동", "202100002", 'F', "19910101", "01034563456")
        val park = patientRepository.inputTestData(hospital, "박길동", "202100003", 'M', "19920101", "01056785678")

        val kimResult: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.NAME, "김", 10, 1)

        val leeResult: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.NAME, "이길", 10, 1)

        val parkResult: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.NAME, "박길동", 10, 1)

        val firstNameResult: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.NAME, "길동", 10, 1)

        assertEquals(1, kimResult.size)
        assertEquals(kim.id, kimResult[0].patientId)
        assertEquals(kim.name, kimResult[0].name)
        assertEquals(kim.registerCode, kimResult[0].registerCode)
        assertEquals(kim.gender, kimResult[0].gender)
        assertEquals(kim.birthday, kimResult[0].birthday)
        assertEquals(kim.phone, kimResult[0].phone)
        assertEquals(null, kimResult[0].recentVisit)

        assertEquals(1, leeResult.size)
        assertEquals(lee.id, leeResult[0].patientId)
        assertEquals(lee.name, leeResult[0].name)
        assertEquals(lee.registerCode, leeResult[0].registerCode)
        assertEquals(lee.gender, leeResult[0].gender)
        assertEquals(lee.birthday, leeResult[0].birthday)
        assertEquals(lee.phone, leeResult[0].phone)
        assertEquals(null, leeResult[0].recentVisit)

        assertEquals(1, parkResult.size)
        assertEquals(park.id, parkResult[0].patientId)
        assertEquals(park.name, parkResult[0].name)
        assertEquals(park.registerCode, parkResult[0].registerCode)
        assertEquals(park.gender, parkResult[0].gender)
        assertEquals(park.birthday, parkResult[0].birthday)
        assertEquals(park.phone, parkResult[0].phone)
        assertEquals(null, parkResult[0].recentVisit)

        assertEquals(3, firstNameResult.size)

        // orderBy registerCode desc
        assertEquals(park.name, firstNameResult[0].name)
        assertEquals(lee.name, firstNameResult[1].name)
        assertEquals(kim.name, firstNameResult[2].name)
    }

    @Test
    fun test_searchPatient_search_by_birthday_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")
        patientRepository.inputTestData(hospital, "이길동", "202100002", 'F', "19910101", "01034563456")
        val park = patientRepository.inputTestData(hospital, "박길동", "202100003", 'M', "19920101", "01056785678")

        val result: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.BIRTHDAY, "1992", 10, 1)

        assertEquals(1, result.size)
        assertEquals(park.id, result[0].patientId)
        assertEquals(park.name, result[0].name)
    }

    @Test
    fun test_searchPatient_search_by_register_code_success() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")
        val lee = patientRepository.inputTestData(hospital, "이길동", "202100002", 'F', "19910101", "01034563456")
        patientRepository.inputTestData(hospital, "박길동", "202100003", 'M', "19920101", "01056785678")

        val result: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.REGISTER_CODE, "202100002", 10, 1)

        assertEquals(1, result.size)
        assertEquals(lee.id, result[0].patientId)
        assertEquals(lee.name, result[0].name)
    }

    @Test
    fun test_searchPatient_return_contains_recentVisit() {
        val hospital = hospitalRepository.inputTestData("우리병원", "10000001", "홍길동")
        val kim = patientRepository.inputTestData(hospital, "김길동", "202100001", 'M', "19900101", "01012341234")
        val lee = patientRepository.inputTestData(hospital, "이길동", "202100002", 'F', "19910101", "01034563456")
        val park = patientRepository.inputTestData(hospital, "박길동", "202100003", 'M', "19920101", "01056785678")
        visitRepository.inputTestData(hospital, kim, LocalDateTime.of(2021, 1, 1, 0, 0, 0), VisitStatus.END)
        visitRepository.inputTestData(hospital, lee, LocalDateTime.of(2021, 1, 2, 0, 0, 0), VisitStatus.CANCEL)
        visitRepository.inputTestData(hospital, park, LocalDateTime.of(2021, 1, 3, 0, 0, 0), VisitStatus.VISITING)
        entityManager.flushAndClear()

        val result: List<SearchPatientDto> =
            patientRepository.searchPatient(hospital.id!!, PatientSearchType.NAME, "길동", 10, 1)

        // orderBy registerCode desc
        assertEquals(3, result.size)
        assertEquals(park.id, result[0].patientId)
        assertEquals(LocalDateTime.of(2021, 1, 3, 0, 0, 0), result[0].recentVisit)
        assertEquals(lee.id, result[1].patientId)
        assertEquals(LocalDateTime.of(2021, 1, 2, 0, 0, 0), result[1].recentVisit)
        assertEquals(kim.id, result[2].patientId)
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0, 0), result[2].recentVisit)
    }
}