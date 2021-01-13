package com.example.demo.patient

interface PatientRepositoryCustom {
    fun findForDetails(hospitalId: Long, id: Long): Patient?
}