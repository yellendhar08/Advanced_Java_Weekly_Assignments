package com.educommerce.resultservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ATTENDANCE-SERVICE")
public interface AttendanceFeignClient {
    @GetMapping("/attendance/percentage/{studentId}")
    ResponseEntity<Double> getAttendancePercentage(@PathVariable("studentId") Long studentId);
}
