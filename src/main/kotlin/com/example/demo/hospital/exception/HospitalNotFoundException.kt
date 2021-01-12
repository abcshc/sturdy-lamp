package com.example.demo.hospital.exception

import com.example.demo.web.exception.HttpErrorException
import org.springframework.http.HttpStatus

class HospitalNotFoundException(message: String = "") : HttpErrorException(message, HttpStatus.NOT_FOUND)