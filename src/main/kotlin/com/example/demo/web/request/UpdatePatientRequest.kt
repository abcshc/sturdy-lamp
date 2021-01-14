package com.example.demo.web.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UpdatePatientRequest(
    @field:NotEmpty(message = "이름을 입력하세요.")
    @field:Size(max = 45, message = "이름이 너무 깁니다.")
    val name: String,
    val gender: Char,
    @field:Pattern(regexp = "^(|[0-9]{8})$", message = "잘못된 생년월일입니다.")
    val birthday: String,
    @field:Size(max = 20, message = "전화번호 길이가 너무 깁니다.")
    val phone: String
)
