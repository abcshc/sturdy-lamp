package com.example.demo.hospital

fun HospitalRepository.inputTestData(name: String, nursingInstitutionCode: String, chiefName: String): Hospital {
    return save(
        Hospital(
            name = name,
            nursingInstitutionCode = nursingInstitutionCode,
            chiefName = chiefName
        )
    )
}