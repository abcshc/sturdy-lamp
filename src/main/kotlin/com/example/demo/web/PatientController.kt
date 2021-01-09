package com.example.demo.web

import com.example.demo.patient.PatientService
import com.example.demo.web.request.RegisterPatientRequest
import com.example.demo.web.response.RegisterPatientResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class PatientController(val patientService: PatientService) {
    @PostMapping("/patient")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun registerPatient(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long, @RequestBody request: RegisterPatientRequest
    ): RegisterPatientResponse {
        return RegisterPatientResponse(
            patientService.registerPatient(
                hospitalId = hospitalId,
                name = request.name,
                gender = request.gender,
                birthday = request.birthday,
                phone = request.phone
            )
        )
    }
}