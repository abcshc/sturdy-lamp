package com.example.demo.web.exception

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class HttpErrorException(message: String, val httpStatus: HttpStatus) : RuntimeException(message)