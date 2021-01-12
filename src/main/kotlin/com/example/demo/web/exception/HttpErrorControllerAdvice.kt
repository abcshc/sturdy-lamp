package com.example.demo.web.exception

import com.example.demo.web.response.HttpErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HttpErrorControllerAdvice {
    @ExceptionHandler(HttpErrorException::class)
    fun handleHttpErrorException(e: HttpErrorException): ResponseEntity<HttpErrorResponse> {
        return ResponseEntity(HttpErrorResponse(e.message ?: ""), e.httpStatus)
    }
}