package com.example.demo.patient

import com.example.demo.hospital.Hospital
import com.example.demo.patient.exception.RegisterCodeOutOfBoundsException
import javax.persistence.*

@Entity
data class PatientRegisterCodeSequence(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    val hospital: Hospital,
    val year: String,
    private var nextVal: Int = 1
) {
    companion object {
        private const val REGISTER_CODE_MAX: Int = 99999
    }

    fun next() {
        if (nextVal >= REGISTER_CODE_MAX) {
            throw RegisterCodeOutOfBoundsException("환자 등록 코드 범위를 초과하였습니다.")
        }
        nextVal += 1
    }

    fun getCode(): String {
        return year + nextVal.toString().padStart(5, '0')
    }
}