package com.example.demo.web.request

data class SearchPatientRequest(
    val type: PatientSearchType,
    val keyword: String,
    val pageSize: Long = 10,
    val pageNo: Long = 1
)
