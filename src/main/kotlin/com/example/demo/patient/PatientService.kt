package com.example.demo.patient

import com.example.demo.web.request.PatientSearchType

interface PatientService {
    fun registerPatient(name: String, gender: Char, birthday: String, phone: String, hospitalId: Long): String
    fun updatePatient(hospitalId: Long, patientId: Long, name: String, gender: Char, birthday: String, phone: String)
    fun deletePatient(hospitalId: Long, patientId: Long)
    fun getPatient(hospitalId: Long, patientId: Long): Patient
    fun searchPatient(
        hospitalId: Long,
        type: PatientSearchType,
        keyword: String,
        pageSize: Long,
        pageNo: Long
    ): List<SearchPatientDto>
}