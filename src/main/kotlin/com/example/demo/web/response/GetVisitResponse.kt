package com.example.demo.web.response

import com.example.demo.visit.Visit
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class GetVisitResponse(
    val visitId: Long,
    val patientName: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val visitTime: LocalDateTime,
    val visitStatusCode: String
) {
    constructor(visit: Visit) : this(visit.id!!, visit.patient.name, visit.visitTime, visit.visitStatusCode)
}
