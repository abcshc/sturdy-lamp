package com.example.demo.patient

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PatientRepository : JpaRepository<Patient, Long> {
    fun findByHospitalIdAndId(hospitalId: Long, patientId: Long): Optional<Patient>
}
