package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.enums.AssignmentStatus;
import org.ra.qltt.model.dto.request.InternshipAssignmentRequestDTO;
import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.service.InternshipAssignmentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/internship_assignments")
@RequiredArgsConstructor
public class InternshipAssignmentsController
{
    private final InternshipAssignmentsService internshipAssignmentsService;

    @GetMapping
    public ResponseEntity<?> getIA (){
        List<InternshipAssignmentResponseDTO> internshipAssignmentsDTO = internshipAssignmentsService.getIA();
        return ResponseEntity.ok(ResponseWrapper.success(internshipAssignmentsDTO,"success.resource.all" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIAById(@PathVariable Long id){
        InternshipAssignmentResponseDTO internshipAssignmentResponseDTO = internshipAssignmentsService.getIAById(id);
        return ResponseEntity.ok(ResponseWrapper.success(internshipAssignmentResponseDTO,"success.resource.find" , HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> addIA(@RequestBody InternshipAssignmentRequestDTO internshipAssignmentRequestDTO){
        InternshipAssignmentResponseDTO internshipAssignmentResponseDTO = internshipAssignmentsService.createIA(internshipAssignmentRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(internshipAssignmentResponseDTO,"success.resource.create",HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatusIA(@PathVariable Long id, @RequestBody  AssignmentStatus status){
        InternshipAssignmentResponseDTO internshipAssignmentResponseDTO = internshipAssignmentsService.updateStatusIA(id , status);
        return ResponseEntity.ok(ResponseWrapper.success(internshipAssignmentResponseDTO, "success.resource.update" , HttpStatus.OK.value()));
    }
}
