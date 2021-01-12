package com.example.demo.patient

interface PatientService {
    fun registerPatient(name: String, gender: Char, birthday: String, phone: String, hospitalId: Long): String
    fun updatePatient(hospitalId: Long, patientId: Long, name: String, gender: Char, birthday: String, phone: String)
    fun deletePatient(hospitalId: Long, patientId: Long)
}