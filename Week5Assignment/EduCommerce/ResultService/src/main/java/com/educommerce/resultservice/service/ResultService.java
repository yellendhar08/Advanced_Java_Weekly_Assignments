package com.educommerce.resultservice.service;

import com.educommerce.resultservice.dto.ResultRequestDTO;
import com.educommerce.resultservice.dto.ResultResponseDTO;
import com.educommerce.resultservice.entity.Result;
import com.educommerce.resultservice.feign.AttendanceFeignClient;
import com.educommerce.resultservice.feign.StudentFeignClient;
import com.educommerce.resultservice.repository.ResultRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private AttendanceFeignClient attendanceFeignClient;

    // ==================== CREATE RESULT ====================
    public ResultResponseDTO createResult(ResultRequestDTO dto) {

        var studentResponse = studentFeignClient.getStudentById(dto.getStudentId());

        if (studentResponse.getBody() == null) {
            throw new RuntimeException("Student not found with ID: " + dto.getStudentId());
        }

        Result result = new Result();
        result.setStudentId(dto.getStudentId());
        result.setCourseId(dto.getCourseId());
        result.setExamType(dto.getExamType());
        result.setMarksObtained(dto.getMarksObtained());
        result.setMaxMarks(dto.getMaxMarks());
        result.setGrade(dto.getGrade());

        return toResponseDTO(resultRepository.save(result));
    }

    // ==================== GET RESULTS BY STUDENT ====================
    @CircuitBreaker(name = "attendanceService", fallbackMethod = "getResultsByStudentFallback")
    public List<ResultResponseDTO> getResultsByStudent(Long studentId) {

        List<Result> results = resultRepository.findByStudentId(studentId);

        Double attendancePercentage =
                attendanceFeignClient.getAttendancePercentage(studentId).getBody();

        return results.stream().map(r -> {
            ResultResponseDTO resDTO = toResponseDTO(r);
            resDTO.setAttendancePercentage(attendancePercentage);
            return resDTO;
        }).collect(Collectors.toList());
    }

    // ==================== CIRCUIT BREAKER FALLBACK ====================
    public List<ResultResponseDTO> getResultsByStudentFallback(Long studentId, Throwable throwable) {

        List<Result> results = resultRepository.findByStudentId(studentId);

        return results.stream().map(r -> {
            ResultResponseDTO resDTO = toResponseDTO(r);
            resDTO.setAttendancePercentage(-1.0);
            return resDTO;
        }).collect(Collectors.toList());
    }

    // ==================== GET RESULTS BY COURSE ====================
    public List<ResultResponseDTO> getResultsByCourse(Long courseId) {

        return resultRepository.findByCourseId(courseId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ==================== UPDATE RESULT ====================
    public ResultResponseDTO updateResult(Long id, ResultRequestDTO dto) {

        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));

        result.setMarksObtained(dto.getMarksObtained());
        result.setMaxMarks(dto.getMaxMarks());
        result.setGrade(dto.getGrade());
        result.setExamType(dto.getExamType());

        return toResponseDTO(resultRepository.save(result));
    }

    // ==================== DELETE RESULT ====================
    public String deleteResult(Long id) {

        if (!resultRepository.existsById(id)) {
            throw new RuntimeException("Result not found with ID: " + id);
        }

        resultRepository.deleteById(id);

        return "Result with ID " + id + " deleted successfully";
    }

    // ==================== ENTITY → DTO ====================
    private ResultResponseDTO toResponseDTO(Result result) {

        ResultResponseDTO dto = new ResultResponseDTO();

        dto.setId(result.getId());
        dto.setStudentId(result.getStudentId());
        dto.setCourseId(result.getCourseId());
        dto.setExamType(result.getExamType());
        dto.setMarksObtained(result.getMarksObtained());
        dto.setMaxMarks(result.getMaxMarks());
        dto.setGrade(result.getGrade());
        dto.setAttendancePercentage(null);

        return dto;
    }
}