package org.ra.qltt.controller;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.RoundCriterionRequestDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.RoundCriterionUpdateRequestDTO;
import org.ra.qltt.model.dto.response.RoundCriterionResponseDTO;
import org.ra.qltt.service.RoundCriteriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/round_criteria")
@RequiredArgsConstructor
public class RoundCriteriaController {
    private final RoundCriteriaService roundCriteriaService;

    @GetMapping
    public ResponseEntity<?> getRC(@RequestParam(name ="round") Long round){
        List<RoundCriterionResponseDTO> roundCriterionResponseDTOS = roundCriteriaService.getRC().stream().filter(RC -> RC.getRoundId().equals(round)).collect(Collectors.toList());
        return ResponseEntity.ok(ResponseWrapper.success(roundCriterionResponseDTOS,"success.resource.all" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRCById(
            @RequestParam(name = "round") Long round,
            @PathVariable Long id
    ) {

        RoundCriterionResponseDTO dto =
                roundCriteriaService.getRCById(id);

        RoundCriterionResponseDTO find = new RoundCriterionResponseDTO();
        if(dto.getRoundId().equals(round)){
            find = roundCriteriaService.getRCById(round);
        }
        else {
            find = null;
        }

        return ResponseEntity.ok(
                ResponseWrapper.success(
                        find,
                        "success.resource.find",
                        HttpStatus.OK.value()
                )
        );
    }

    @PostMapping
    public ResponseEntity<?> saveRC(@RequestBody RoundCriterionRequestDTO roundCriterionRequestDTO){
        RoundCriterionResponseDTO roundCriterionResponseDTO = roundCriteriaService.createRC(roundCriterionRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(roundCriterionResponseDTO,"success.resource.create",HttpStatus.CREATED.value()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRC(@PathVariable Long id, @RequestBody RoundCriterionUpdateRequestDTO roundCriterionRequestDTO){
        RoundCriterionResponseDTO  roundCriterionResponseDTO = roundCriteriaService.updateRC(id, roundCriterionRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(roundCriterionResponseDTO,"success.resource.update",HttpStatus.OK.value()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRC(@PathVariable Long id){
        roundCriteriaService.deleteRC(id);
        return ResponseEntity.ok(ResponseWrapper.success(null,"success.resource.delete",HttpStatus.OK.value()));
    }
}
