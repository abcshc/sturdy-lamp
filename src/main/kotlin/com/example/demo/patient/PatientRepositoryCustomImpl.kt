package com.example.demo.patient

import com.example.demo.visit.QVisit
import com.example.demo.web.request.PatientSearchType
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PatientRepositoryCustomImpl(private val jpaQueryFactory: JPAQueryFactory) : PatientRepositoryCustom {
    private val patient: QPatient = QPatient.patient
    override fun findForDetails(hospitalId: Long, id: Long): Patient? {
        return jpaQueryFactory.selectFrom(patient)
            .leftJoin(patient.visits).fetchJoin()
            .where(
                patient.id.eq(id)
                    .and(patient.hospital.id.eq(hospitalId))
                    .and(patient.deleted.isFalse)
            ).fetchOne()
    }

    override fun searchPatient(
        hospitalId: Long,
        type: PatientSearchType,
        keyword: String,
        pageSize: Long,
        pageNo: Long
    ): List<SearchPatientDto> {
        val condition: Predicate = when (type) {
            PatientSearchType.NAME -> patient.name.like("%$keyword%")
            PatientSearchType.REGISTER_CODE -> patient.registerCode.like("%$keyword%")
            PatientSearchType.BIRTHDAY -> patient.registerCode.like("%$keyword%")
        }
        val visit = QVisit("visit")
        return jpaQueryFactory.select(
            patient.id,
            patient.name,
            patient.registerCode,
            patient.gender,
            patient.birthday,
            patient.phone,
            visit.visitTime.max()
        )
            .from(patient)
            .leftJoin(patient.visits, visit)
            .where(
                patient.hospital.id.eq(hospitalId)
                    .and(patient.deleted.isFalse)
                    .and(condition)
            )
            .groupBy(patient.id)
            .offset((pageNo - 1) * pageSize)
            .limit(pageSize)
            .fetch()
            .map {
                SearchPatientDto(
                    it.get(patient.id)!!,
                    it.get(patient.name)!!,
                    it.get(patient.registerCode)!!,
                    it.get(patient.gender)!!,
                    it.get(patient.birthday)!!,
                    it.get(patient.phone),
                    it.get(visit.visitTime.max())
                )
            }
    }
}