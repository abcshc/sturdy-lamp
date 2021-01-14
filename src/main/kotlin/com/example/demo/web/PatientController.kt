package com.example.demo.web

import com.example.demo.patient.PatientService
import com.example.demo.web.request.RegisterPatientRequest
import com.example.demo.web.request.SearchPatientRequest
import com.example.demo.web.request.UpdatePatientRequest
import com.example.demo.web.response.GetPatientResponse
import com.example.demo.web.response.RegisterPatientResponse
import com.example.demo.web.response.SearchPatientResponse
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

    @PutMapping("/patient/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePatient(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long, @RequestBody request: UpdatePatientRequest,
        @PathVariable patientId: Long
    ) {
        patientService.updatePatient(
            hospitalId = hospitalId,
            patientId = patientId,
            name = request.name,
            gender = request.gender,
            birthday = request.birthday,
            phone = request.phone
        )
    }

    @DeleteMapping("/patient/{patientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePatient(@RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long, @PathVariable patientId: Long) {
        patientService.deletePatient(hospitalId, patientId)
    }

    @GetMapping("/patient/{patientId}")
    @ResponseBody
    fun getPatient(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long,
        @PathVariable patientId: Long
    ): GetPatientResponse {
        return GetPatientResponse(patientService.getPatient(hospitalId, patientId))
    }

    @GetMapping("/patient")
    @ResponseBody
    fun searchPatient(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long,
        @RequestBody request: SearchPatientRequest
    ): SearchPatientResponse {
        return SearchPatientResponse(
            patientService.searchPatient(
                hospitalId,
                request.type,
                request.keyword,
                request.pageSize,
                request.pageNo
            )
        )
    }
}