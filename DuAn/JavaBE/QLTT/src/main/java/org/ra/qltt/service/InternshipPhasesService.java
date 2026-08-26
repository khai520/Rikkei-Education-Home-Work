package org.ra.qltt.service;

import org.ra.qltt.model.dto.request.InternshipPhaseRequestDTO;
import org.ra.qltt.model.dto.response.InternshipPhaseResponseDTO;

import java.util.List;

public interface InternshipPhasesService {
    List<InternshipPhaseResponseDTO> getIP();
    InternshipPhaseResponseDTO getIPById(Long id);
    InternshipPhaseResponseDTO createIP (InternshipPhaseRequestDTO internshipPhaseRequestDTO);
    InternshipPhaseResponseDTO updateIP (InternshipPhaseRequestDTO internshipPhaseRequestDTO , Long id);
    void deleteIP (Long id );
}
