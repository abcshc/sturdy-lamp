package com.example.demo.patient.exception

import com.example.demo.web.exception.HttpErrorException
import org.springframework.http.HttpStatus

class PatientNotFoundException(message: String = "") : HttpErrorException(message, HttpStatus.NOT_FOUND)