package com.example.demo.patient

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class SearchPatientDto(
    val patientId: Long,
    val name: String,
    val registerCode: String,
    val gender: String,
    val birthday: String? = "",
    val phone: String? = "",
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val recentVisit: LocalDateTime?
)