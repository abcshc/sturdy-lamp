package com.example.demo.hospital.exception

import com.example.demo.web.exception.HttpErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class HospitalNotFoundException(message: String) : HttpErrorResponse(message)