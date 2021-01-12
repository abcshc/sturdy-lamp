package com.example.demo.web.request

data class UpdatePatientRequest(
    val name: String,
    val gender: Char,
    val birthday: String,
    val phone: String
)
