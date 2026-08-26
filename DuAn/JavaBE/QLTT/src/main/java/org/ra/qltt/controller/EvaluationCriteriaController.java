package org.ra.qltt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.EvaluationCriterionRequestDTO;
import org.ra.qltt.model.dto.response.EvaluationCriterionResponseDTO;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.ra.qltt.service.EvaluationCriteriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation_criteria")
@RequiredArgsConstructor
public class EvaluationCriteriaController {
    private final EvaluationCriteriaService evaluationCriteriaService;

    @GetMapping
    public ResponseEntity<?> getEC(){
        List<EvaluationCriterionResponseDTO> evaluationCriterionResponseDTO = evaluationCriteriaService.getEC();
        return ResponseEntity.ok(ResponseWrapper.success(evaluationCriterionResponseDTO,"Lấy danh sách thành công" , HttpStatus.OK.value()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getECById(@PathVariable Long id){
        EvaluationCriterionResponseDTO evaluationCriterionResponseDTO = evaluationCriteriaService.getECById(id);
        return ResponseEntity.ok(ResponseWrapper.success(evaluationCriterionResponseDTO,"Lấy dữ liệu thành công" , HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> createEC(@Valid @RequestBody EvaluationCriterionRequestDTO evaluationCriterionRequestDTO){
        EvaluationCriterionResponseDTO evaluationCriterionResponseDTO = evaluationCriteriaService.createEC(evaluationCriterionRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(evaluationCriterionResponseDTO,"Thêm mới thành công",HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEC(@Valid @RequestBody EvaluationCriterionRequestDTO evaluationCriterionRequestDTO , @PathVariable Long id){
        EvaluationCriterionResponseDTO evaluationCriterionResponseDTO = evaluationCriteriaService.updateEC(evaluationCriterionRequestDTO , id);
        return ResponseEntity.ok(ResponseWrapper.success(evaluationCriterionResponseDTO,"Sửa dữ liệu thành công",HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEC(@PathVariable Long id){
        evaluationCriteriaService.deleteEC(id);
        return ResponseEntity.ok(ResponseWrapper.success(null,"Xóa dữ liệu thành công",HttpStatus.OK.value()));
    }
}
