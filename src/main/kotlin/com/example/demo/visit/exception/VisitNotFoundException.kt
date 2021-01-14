package com.example.demo.visit.exception

import com.example.demo.web.exception.HttpErrorException
import org.springframework.http.HttpStatus

class VisitNotFoundException(message: String = "") : HttpErrorException(message, HttpStatus.NOT_FOUND)
