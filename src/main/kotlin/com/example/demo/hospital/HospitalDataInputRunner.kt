package com.example.demo.hospital

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class HospitalDataInputRunner(val hospitalRepository: HospitalRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        if (!hospitalRepository.existsById(1L)) {
            hospitalRepository.save(
                Hospital(
                    id = 1L,
                    name = "우리병원",
                    nursingInstitutionCode = "10000001",
                    chiefName = "홍길동"
                )
            )
        }

        if (!hospitalRepository.existsById(2L)) {
            hospitalRepository.save(
                Hospital(
                    id = 2L,
                    name = "우리대학병원",
                    nursingInstitutionCode = "10000002",
                    chiefName = "고길동"
                )
            )
        }

        if (!hospitalRepository.existsById(3L)) {
            hospitalRepository.save(
                Hospital(
                    id = 3L,
                    name = "우리종합병원",
                    nursingInstitutionCode = "10000003",
                    chiefName = "김길동"
                )
            )
        }
    }
}