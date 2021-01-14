package com.example.demo.visit

import org.springframework.data.jpa.repository.JpaRepository

interface VisitRepository : JpaRepository<Visit, Long> {
    fun findByHospitalIdAndIdAndDeletedFalse(hospitalId: Long, visitId: Long): Visit?
}