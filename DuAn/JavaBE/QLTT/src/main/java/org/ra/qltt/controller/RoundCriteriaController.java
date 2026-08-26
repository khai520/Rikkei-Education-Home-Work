package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.model.dto.response.ResponseWrapper;
import org.ra.qltt.model.dto.response.RoundCriterionResponseDTO;
import org.ra.qltt.service.RoundCriteriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/round_criteria")
@RequiredArgsConstructor
public class RoundCriteriaController {
    private final RoundCriteriaService roundCriteriaService;
    private final MessageSourceConfig messageSourceConfig;

    @GetMapping
    public ResponseEntity<?> getRC(@RequestParam(name ="round") Long round){
        List<RoundCriterionResponseDTO> roundCriterionResponseDTOS = roundCriteriaService.getRC().stream().filter(RC -> RC.getRoundId().equals(round)).collect(Collectors.toList());;
        return ResponseEntity.ok(ResponseWrapper.success(roundCriterionResponseDTOS,"Lấy danh sách thành công" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRCById(
            @RequestParam(name = "round") Long round,
            @PathVariable Long id
    ) {

        RoundCriterionResponseDTO dto =
                roundCriteriaService.getRCById(id);

        if (!Objects.equals(dto.getRoundId(), round)) {
            throw new ResourceNotFoundException(
                    "Tiêu chí không thuộc đợt đánh giá với ID: " + round
            );
        }

        return ResponseEntity.ok(
                ResponseWrapper.success(
                        dto,
                        "Lấy dữ liệu thành công",
                        HttpStatus.OK.value()
                )
        );
    }


}
