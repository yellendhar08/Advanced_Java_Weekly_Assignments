package com.educommerce.resultservice.controller;

import com.educommerce.resultservice.dto.ResultRequestDTO;
import com.educommerce.resultservice.dto.ResultResponseDTO;
import com.educommerce.resultservice.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResponseEntity<ResultResponseDTO> createResult(@RequestBody ResultRequestDTO dto) {
        return new ResponseEntity<>(resultService.createResult(dto), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ResultResponseDTO>> getResultsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(resultService.getResultsByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ResultResponseDTO>> getResultsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(resultService.getResultsByCourse(courseId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponseDTO> updateResult(@PathVariable Long id, @RequestBody ResultRequestDTO dto) {
        return ResponseEntity.ok(resultService.updateResult(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResult(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.deleteResult(id));
    }

}
