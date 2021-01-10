package com.example.demo.hospital

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HospitalRepository : JpaRepository<Hospital, Long>