package com.example.demo.web.response

import com.example.demo.patient.Patient

data class GetPatientResponse(
    val id: Long,
    val name: String,
    val gender: String,
    val birthday: String,
    val phone: String,
    val visits: List<VisitResponse>
) {
    constructor(patient: Patient) : this(
        patient.id!!,
        patient.name,
        patient.gender,
        patient.birthday ?: "",
        patient.phone ?: "",
        patient.visits.map { VisitResponse(it) }
    )
}

