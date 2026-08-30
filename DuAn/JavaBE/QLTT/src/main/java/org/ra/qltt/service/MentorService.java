package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.MentorRequestDTO;
import org.ra.qltt.model.dto.request.MentorUpdateRequestDTO;
import org.ra.qltt.model.dto.response.MentorResponseDTO;

import java.util.List;

public interface MentorService {
    List<MentorResponseDTO> getMentor();
    MentorResponseDTO findMentorByID(Long id);
    MentorResponseDTO createMentor(MentorRequestDTO mentorRequestDTO);
    MentorResponseDTO updateMentor(MentorUpdateRequestDTO mentorRequestDTO , Long id);
}
