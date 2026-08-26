package org.ra.qltt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.InternshipPhaseRequestDTO;
import org.ra.qltt.model.dto.response.InternshipPhaseResponseDTO;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.ra.qltt.service.InternshipPhasesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internship_phases")
@RequiredArgsConstructor
public class InternshipPhasesController {

    private final InternshipPhasesService internshipPhasesService;

    @GetMapping
    public ResponseEntity<?> getIP(){
        List<InternshipPhaseResponseDTO> internshipPhaseResponseDTOList = internshipPhasesService.getIP();
        return ResponseEntity.ok(ResponseWrapper.success(internshipPhaseResponseDTOList,"Lấy danh sách thành công" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIPById(@PathVariable Long id){
        InternshipPhaseResponseDTO internshipPhaseResponseDTO = internshipPhasesService.getIPById(id);
        return ResponseEntity.ok(ResponseWrapper.success(internshipPhaseResponseDTO,"Lấy dữ liệu thành công" , HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> createIP(@Valid @RequestBody InternshipPhaseRequestDTO internshipPhaseRequestDTO){
        InternshipPhaseResponseDTO internshipPhaseResponseDTO = internshipPhasesService.createIP(internshipPhaseRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(internshipPhaseResponseDTO,"Thêm mới thành công",HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIP(@Valid @RequestBody InternshipPhaseRequestDTO internshipPhaseRequestDTO , @PathVariable Long id){
        InternshipPhaseResponseDTO internshipPhaseResponseDTO = internshipPhasesService.updateIP(internshipPhaseRequestDTO, id);
        return ResponseEntity.ok(
                ResponseWrapper.success(
                        internshipPhaseResponseDTO,
                        "Cập nhật thông tin sinh viên thành công",
                        HttpStatus.OK.value()
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIP (@PathVariable Long id){
        internshipPhasesService.deleteIP(id);
        return ResponseEntity.ok(ResponseWrapper.success(null,"Xóa dữ liệu thành công",HttpStatus.OK.value()));
    }
}
