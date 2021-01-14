package com.example.demo.web

import com.example.demo.visit.VisitService
import com.example.demo.web.request.PostVisitRequest
import com.example.demo.web.request.UpdateVisitRequest
import com.example.demo.web.response.GetVisitResponse
import com.example.demo.web.response.PostVisitResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class VisitController(private val visitService: VisitService) {
    @PostMapping("/visit")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun postVisit(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long,
        @RequestBody request: PostVisitRequest
    ): PostVisitResponse {
        return PostVisitResponse(visitService.visit(hospitalId, request.patientId))
    }

    @PutMapping("/visit/{visitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateVisit(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long,
        @PathVariable visitId: Long,
        @RequestBody request: UpdateVisitRequest
    ) {
        visitService.updateVisit(hospitalId, visitId, request.visitStatusCode)
    }

    @DeleteMapping("/visit/{visitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteVisit(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long,
        @PathVariable visitId: Long
    ) {
        visitService.deleteVisit(hospitalId, visitId)
    }

    @GetMapping("/visit/{visitId}")
    @ResponseBody
    fun getVisit(
        @RequestHeader(value = "X-HOSPITAL-ID") hospitalId: Long,
        @PathVariable visitId: Long
    ): GetVisitResponse {
        return GetVisitResponse(visitService.getVisit(hospitalId, visitId))
    }
}