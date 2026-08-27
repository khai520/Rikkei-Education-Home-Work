package org.ra.qltt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ra.qltt.model.dto.request.MentorRequestDTO;
import org.ra.qltt.model.dto.response.MentorResponseDTO;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.service.MentorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentors")
@RequiredArgsConstructor
public class MentorController {
    private final MentorService mentorService;

    @GetMapping
    public ResponseEntity<?> getMentors() {
        List<MentorResponseDTO> responseDTOS = mentorService.getMentor();
        return ResponseEntity.ok(ResponseWrapper.success(responseDTOS, "success.resource.all" , HttpStatus.OK.value()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMentorById(@PathVariable Long id) {
        MentorResponseDTO responseDTO = mentorService.findMentorByID(id);
        return ResponseEntity.ok(ResponseWrapper.success(responseDTO, "success.resource.find" , HttpStatus.OK.value()));
    }

    @PostMapping
    public ResponseEntity<?> createMentor(@Valid @RequestBody MentorRequestDTO mentorRequestDTO) {
        MentorResponseDTO responseDTO = mentorService.createMentor(mentorRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success(responseDTO,"success.resource.create",HttpStatus.CREATED.value()));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateMentor(@PathVariable Long id, @Valid @RequestBody MentorRequestDTO mentorRequestDTO) {
        MentorResponseDTO responseDTO = mentorService.updateMentor(mentorRequestDTO, id);
        return ResponseEntity.ok(
                ResponseWrapper.success(
                        responseDTO,
                        "success.resource.update",
                        HttpStatus.OK.value()
                )
        );
    }
}
