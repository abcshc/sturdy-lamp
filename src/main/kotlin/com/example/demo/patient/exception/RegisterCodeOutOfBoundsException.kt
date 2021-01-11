package com.example.demo.patient.exception

import com.example.demo.web.exception.HttpErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_EXTENDED)
class RegisterCodeOutOfBoundsException(message: String) : HttpErrorResponse(message)