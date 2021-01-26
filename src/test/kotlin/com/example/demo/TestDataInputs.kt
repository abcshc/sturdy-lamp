package com.example.demo

import com.example.demo.hospital.Hospital
import com.example.demo.hospital.HospitalRepository
import com.example.demo.patient.Patient
import com.example.demo.patient.PatientRepository
import com.example.demo.visit.Visit
import com.example.demo.visit.VisitRepository
import com.example.demo.visit.VisitStatus
import java.time.LocalDateTime
import javax.persistence.EntityManager

fun EntityManager.flushAndClear() {
    flush()
    clear()
}

fun HospitalRepository.inputTestData(
    name: String,
    nursingInstitutionCode: String,
    chiefName: String
): Hospital {
    return save(
        Hospital(
            name = name,
            nursingInstitutionCode = nursingInstitutionCode,
            chiefName = chiefName
        )
    )
}

fun PatientRepository.inputTestData(
    hospital: Hospital,
    name: String,
    registerCode: String,
    gender: Char,
    birthday: String = "",
    phone: String = ""
): Patient {
    return save(
        Patient(
            hospital = hospital,
            name = name,
            registerCode = registerCode,
            gender = gender.toString(),
            birthday = birthday,
            phone = phone
        )
    )
}

fun VisitRepository.inputTestData(
    hospital: Hospital,
    patient: Patient,
    visitTime: LocalDateTime,
    visitStatus: VisitStatus
): Visit {
    return save(
        Visit(
            hospital = hospital,
            patient = patient,
            visitTime = visitTime,
            visitStatusCode = visitStatus.code
        )
    )
}