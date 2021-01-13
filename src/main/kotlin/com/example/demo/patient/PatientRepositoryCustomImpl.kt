package com.example.demo.patient

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PatientRepositoryCustomImpl(private val jpaQueryFactory: JPAQueryFactory) : PatientRepositoryCustom {
    private val patient: QPatient = QPatient.patient
    override fun findForDetails(hospitalId: Long, id: Long): Patient? {
        return jpaQueryFactory.selectFrom(patient)
            .join(patient.visits).fetchJoin()
            .where(
                patient.id.eq(id)
                    .and(patient.hospital.id.eq(hospitalId))
                    .and(patient.deleted.isFalse)
            ).fetchOne()
    }
}