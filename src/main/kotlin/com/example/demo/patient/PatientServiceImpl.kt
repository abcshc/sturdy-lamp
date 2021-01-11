package com.example.demo.patient

import com.example.demo.hospital.Hospital
import com.example.demo.hospital.HospitalRepository
import com.example.demo.hospital.exception.HospitalNotFoundException
import org.springframework.stereotype.Service
import java.time.Year
import javax.transaction.Transactional

@Service
class PatientServiceImpl(
    val patientRepository: PatientRepository,
    val patientRegisterCodeSequenceRepository: PatientRegisterCodeSequenceRepository,
    val hospitalRepository: HospitalRepository
) : PatientService {
    @Transactional
    override fun registerPatient(
        name: String,
        gender: Char,
        birthday: String,
        phone: String,
        hospitalId: Long
    ): String {
        val hospital: Hospital =
            hospitalRepository.findById(hospitalId).orElseThrow { HospitalNotFoundException("병원정보를 찾을 수 없습니다.") }
        val registerCodeSequence: PatientRegisterCodeSequence =
            getCurrentRegisterCodeSequence(hospital, Year.now())
        val patient: Patient = patientRepository.save(
            Patient(
                hospital = hospital,
                name = name,
                registerCode = registerCodeSequence.getCode(),
                gender = gender.toString(),
                birthday = birthday,
                phone = phone
            )
        )
        registerCodeSequence.next()
        return patient.registerCode
    }

    private fun getCurrentRegisterCodeSequence(hospital: Hospital, year: Year): PatientRegisterCodeSequence {
        // TODO: 동시성 처리 -> redis 사용
        return patientRegisterCodeSequenceRepository.findByHospitalIdAndYear(hospital.id!!, year.toString())
            ?: patientRegisterCodeSequenceRepository.save(
                PatientRegisterCodeSequence(
                    hospital = hospital,
                    year = year.toString()
                )
            )

    }
}