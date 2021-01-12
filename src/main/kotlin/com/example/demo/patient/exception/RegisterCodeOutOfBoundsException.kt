package com.example.demo.patient.exception

import com.example.demo.web.exception.HttpErrorException
import org.springframework.http.HttpStatus

class RegisterCodeOutOfBoundsException(message: String = "") : HttpErrorException(message, HttpStatus.NOT_EXTENDED)