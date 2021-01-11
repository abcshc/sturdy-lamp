package com.example.demo.patient

import org.springframework.data.jpa.repository.JpaRepository

interface PatientRegisterCodeSequenceRepository : JpaRepository<PatientRegisterCodeSequence, Long> {
    fun findByHospitalIdAndYear(hospitalId: Long, year: String): PatientRegisterCodeSequence?
}