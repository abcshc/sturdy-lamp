package com.example.demo.visit

interface VisitService {
    fun visit(hospitalId: Long, patientId: Long): Long
    fun updateVisit(hospitalId: Long, visitId: Long, visitStatusCode: String)
    fun deleteVisit(hospitalId: Long, visitId: Long)
    fun getVisit(hospitalId: Long, visitId: Long): Visit
}
