package com.example.demo.patient

import com.example.demo.hospital.Hospital
import javax.persistence.*

@Entity
data class Patient(
    // TODO: 조회 시 getter 설정 필요
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(optional = false)
    val hospital: Hospital,
    @Column(length = 45, nullable = false)
    private var name: String,
    @Column(length = 13, nullable = false)
    val registerCode: String,
    @Column(length = 10, nullable = false)
    private var gender: String,
    @Column(length = 10, nullable = true)
    private var birthday: String?,
    @Column(length = 20, nullable = true)
    private var phone: String?,
    private var deleted: Boolean = false
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