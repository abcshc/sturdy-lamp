package com.example.demo.web.exception

import com.example.demo.web.response.HttpErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class HttpErrorControllerAdvice {
    @ExceptionHandler(HttpErrorException::class)
    fun handleHttpErrorException(e: HttpErrorException): ResponseEntity<HttpErrorResponse> {
        return ResponseEntity(HttpErrorResponse(e.message!!), e.httpStatus)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<HttpErrorResponse> {
        return ResponseEntity(HttpErrorResponse("잘못된 요청입니다."), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<HttpErrorResponse> {
        return ResponseEntity(
            HttpErrorResponse(e.bindingResult.allErrors[0].defaultMessage ?: "잘못된 요청입니다."),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<HttpErrorResponse> {
        return ResponseEntity(HttpErrorResponse(e.message ?: "서버 에러가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}