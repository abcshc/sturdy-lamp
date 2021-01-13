package com.example.demo.visit

import com.example.demo.hospital.Hospital
import com.example.demo.patient.Patient
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne
    val hospital: Hospital,
    @ManyToOne
    val patient: Patient,
    val visitTime: LocalDateTime,
    val visitStatusCode: String
)
