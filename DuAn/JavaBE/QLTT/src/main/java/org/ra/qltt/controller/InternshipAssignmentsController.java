package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.response.InternshipAssignmentResponseDTO;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.ra.qltt.service.InternshipAssignmentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/internship_assigments")
@RequiredArgsConstructor
public class InternshipAssignmentsController
{
    private final InternshipAssignmentsService internshipAssignmentsService;

    @GetMapping
    public ResponseEntity<?> getIA (){
        List<InternshipAssignmentResponseDTO> internshipAssignmentsDTO = internshipAssignmentsService.getIA();
        return ResponseEntity.ok(ResponseWrapper.success(internshipAssignmentsDTO,"Lấy danh sách thành công" , HttpStatus.OK.value()));
    }

}
