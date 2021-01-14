package com.example.demo.patient

import com.example.demo.web.request.PatientSearchType

interface PatientRepositoryCustom {
    fun findForDetails(hospitalId: Long, id: Long): Patient?
    fun searchPatient(
        hospitalId: Long,
        type: PatientSearchType,
        keyword: String,
        pageSize: Long,
        pageNo: Long
    ): List<SearchPatientDto>
}