package com.example.demo.hospital

import javax.persistence.*

@Entity
data class Hospital(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(length = 45, nullable = false)
    val name: String,
    @Column(length = 20, nullable = false)
    val nursingInstitutionCode: String,
    @Column(length = 10, nullable = false)
    val chiefName: String
)