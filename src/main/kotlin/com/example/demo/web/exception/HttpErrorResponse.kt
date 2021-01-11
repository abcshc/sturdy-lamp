package com.example.demo.web.exception

import java.lang.RuntimeException

open class HttpErrorResponse(message: String) : RuntimeException(message)