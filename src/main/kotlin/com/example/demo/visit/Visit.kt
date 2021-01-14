package com.example.demo.visit

import com.example.demo.hospital.Hospital
import com.example.demo.patient.Patient
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Visit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    val hospital: Hospital,
    @ManyToOne
    val patient: Patient,
    val visitTime: LocalDateTime,
    var visitStatusCode: String,
    private var deleted: Boolean = false
) {
    fun update(visitStatusCode: String) {
        this.visitStatusCode = visitStatusCode
    }

    fun delete() {
        this.deleted = true
    }
}
