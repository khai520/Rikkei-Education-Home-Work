package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.AssessmentResultRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentResultResponseDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.service.AssessmentResultsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assessment_results")
@RequiredArgsConstructor
public class AssessmentResultsController {
    private final AssessmentResultsService  assessmentResultsService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam (name = "assignment" , required = false) Long assignmentId) {
        List<AssessmentResultResponseDTO> assessmentResultResponseDTO = assessmentResultsService.findAR();
        if(assignmentId != null){
            assessmentResultResponseDTO = assessmentResultResponseDTO.stream().filter(ar -> ar.getAssignmentId().equals(assignmentId)).collect(Collectors.toList());
        }
        return ResponseEntity.ok(ResponseWrapper.success(assessmentResultResponseDTO,"success.resource.all" , HttpStatus.OK.value()));
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AssessmentResultRequestDTO assessmentResultRequestDTO) {
        AssessmentResultResponseDTO assessmentResultResponseDTO = assessmentResultsService.createAR(assessmentResultRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(assessmentResultResponseDTO, "success.resource.create" , HttpStatus.OK.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateScore(@PathVariable Long id, @RequestBody BigDecimal score) {
        AssessmentResultResponseDTO assessmentResultResponseDTO = assessmentResultsService.updateAR(id, score);
        return ResponseEntity.ok(ResponseWrapper.success(assessmentResultResponseDTO, "success.resource.update" , HttpStatus.OK.value()));
    }
}
