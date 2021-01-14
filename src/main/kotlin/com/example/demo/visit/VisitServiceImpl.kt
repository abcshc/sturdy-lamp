package com.example.demo.visit

import com.example.demo.hospital.HospitalRepository
import com.example.demo.hospital.exception.HospitalNotFoundException
import com.example.demo.patient.PatientRepository
import com.example.demo.patient.exception.PatientNotFoundException
import com.example.demo.visit.exception.VisitNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VisitServiceImpl(
    private val visitRepository: VisitRepository,
    private val hospitalRepository: HospitalRepository,
    private val patientRepository: PatientRepository
) : VisitService {
    override fun visit(hospitalId: Long, patientId: Long): Long {
        val hospital = hospitalRepository.findById(hospitalId)
            .orElseThrow { throw HospitalNotFoundException("병원 정보를 찾을 수 없습니다.") }
        val patient = patientRepository.findByHospitalIdAndIdAndDeletedFalse(hospitalId, patientId)
            ?: throw PatientNotFoundException("환자 정보를 찾을 수 없습니다.")
        return visitRepository.save(
            Visit(
                hospital = hospital,
                patient = patient,
                visitTime = LocalDateTime.now(),
                visitStatusCode = VisitStatus.VISITING.code
            )
        ).id!!
    }

    override fun updateVisit(hospitalId: Long, visitId: Long, visitStatusCode: String) {
        val visit = visitRepository.findByHospitalIdAndIdAndDeletedFalse(hospitalId, visitId)
            ?: throw VisitNotFoundException("방문 정보를 찾을 수 없습니다.")
        visit.update(visitStatusCode)
        visitRepository.save(visit)
    }

    override fun deleteVisit(hospitalId: Long, visitId: Long) {
        val visit = visitRepository.findByHospitalIdAndIdAndDeletedFalse(hospitalId, visitId)
            ?: throw VisitNotFoundException("방문 정보를 찾을 수 없습니다.")
        visit.delete()
        visitRepository.save(visit)
    }

    override fun getVisit(hospitalId: Long, visitId: Long): Visit {
        return visitRepository.findByHospitalIdAndIdAndDeletedFalse(hospitalId, visitId)
            ?: throw VisitNotFoundException("방문 정보를 찾을 수 없습니다.")
    }
}