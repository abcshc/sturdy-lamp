package com.example.demo.patient

interface PatientService {
    fun registerPatient(name: String, gender: Char, birthday: String, phone: String, hospitalId: Long): String
}