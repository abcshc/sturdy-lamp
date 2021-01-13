package com.example.demo.patient

import com.example.demo.hospital.Hospital
import com.example.demo.visit.Visit
import javax.persistence.*

@Entity
data class Patient(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false)
    val hospital: Hospital,
    @Column(length = 45, nullable = false)
    var name: String,
    @Column(length = 13, nullable = false)
    val registerCode: String,
    @Column(length = 10, nullable = false)
    var gender: String,
    @Column(length = 10, nullable = true)
    var birthday: String?,
    @Column(length = 20, nullable = true)
    var phone: String?,
    private var deleted: Boolean = false,
    @OneToMany(mappedBy = "patient")
    var visits: List<Visit> = ArrayList()
) {
    fun update(name: String, gender: String, birthday: String, phone: String) {
        this.name = name
        this.gender = gender
        this.birthday = birthday
        this.phone = phone
    }

    fun delete() {
        deleted = true
    }
}