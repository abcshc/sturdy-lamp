package com.example.demo.web.request

import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class SearchPatientRequest(
    val type: PatientSearchType,
    @field:Size(max = 45, message = "검색어가 너무 깁니다.")
    val keyword: String,
    val pageSize: Long = 10,
    @field:Min(value = 1, message = "잘못된 페이지 번호입니다.")
    val pageNo: Long = 1
)
