package com.example.demo.patient

import org.springframework.stereotype.Service

@Service
class PatientServiceImpl : PatientService {
    override fun registerPatient(name: String, gender: Char, birthday: String, phone: String, hospitalId: Long): String {
        TODO("Not yet implemented")
    }
}