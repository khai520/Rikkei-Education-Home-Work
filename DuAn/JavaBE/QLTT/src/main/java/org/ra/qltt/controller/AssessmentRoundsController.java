package org.ra.qltt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.AssessmentRoundRequestDTO;
import org.ra.qltt.model.dto.request.AssessmentRoundUpdateRequestDTO;
import org.ra.qltt.model.dto.response.AssessmentRoundResponseDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.service.AssessmentRoundsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assessment_rounds")
@RequiredArgsConstructor
public class AssessmentRoundsController {
    private final AssessmentRoundsService assessmentRoundsService;

    @GetMapping
    public ResponseEntity<?> getAR(@RequestParam(name = "phase"  , required = false) Long phase){
        List<AssessmentRoundResponseDTO> assessmentRoundResponseDTOList = assessmentRoundsService.getAR();
        if(phase != null){
            assessmentRoundResponseDTOList = assessmentRoundResponseDTOList.stream().filter(ar -> ar.getPhaseId().equals(phase)).collect(Collectors.toList());
        }
        return ResponseEntity.ok(ResponseWrapper.success(assessmentRoundResponseDTOList,"success.resource.all" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getARById(@PathVariable Long id){
        AssessmentRoundResponseDTO assessmentRoundResponseDTO = assessmentRoundsService.getARById(id);
        return ResponseEntity.ok(ResponseWrapper.success(assessmentRoundResponseDTO,"success.resource.find" , HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> createAR(@Valid @RequestBody AssessmentRoundRequestDTO assessmentRoundRequestDTO){
        AssessmentRoundResponseDTO assessmentRoundResponseDTO = assessmentRoundsService.createAR(assessmentRoundRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(assessmentRoundResponseDTO,"success.resource.create",HttpStatus.CREATED.value()));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateAR(@Valid @RequestBody AssessmentRoundUpdateRequestDTO assessmentRoundRequestDTO, @PathVariable Long id){
        AssessmentRoundResponseDTO assessmentRoundResponseDTO = assessmentRoundsService.updateAR(assessmentRoundRequestDTO , id);
        return ResponseEntity.ok(ResponseWrapper.success(assessmentRoundResponseDTO,"success.resource.update",HttpStatus.OK.value()));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAR(@PathVariable Long id){
        assessmentRoundsService.deleteAR(id);
        return ResponseEntity.ok(ResponseWrapper.success(null,"success.resource.delete",HttpStatus.OK.value()));
    }
}
