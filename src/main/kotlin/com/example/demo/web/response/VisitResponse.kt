package com.example.demo.web.response

import com.example.demo.visit.Visit
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class VisitResponse(
    val id: Long,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    val visitTime: LocalDateTime,
    val visitStatusCode: String
) {
    constructor(visit: Visit) : this(visit.id, visit.visitTime, visit.visitStatusCode)
}