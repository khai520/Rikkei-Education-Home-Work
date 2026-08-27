package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;

import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.request.InternshipPhaseRequestDTO;
import org.ra.qltt.model.dto.response.InternshipPhaseResponseDTO;
import org.ra.qltt.model.entity.InternshipPhases;
import org.ra.qltt.model.mapper.InternshipPhaseMapper;
import org.ra.qltt.repository.InternshipPhasesRepository;
import org.ra.qltt.service.InternshipPhasesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipPhasesServiceImpl implements InternshipPhasesService {

    private final InternshipPhasesRepository IPR;
    private final InternshipPhaseMapper internshipPhaseMapper;

    @Override
    public List<InternshipPhaseResponseDTO> getIP() {
        List<InternshipPhases> IP = IPR.findAll();
        return internshipPhaseMapper.internshipPhasesToResponseDTOs(IP);
    }

    @Override
    public InternshipPhaseResponseDTO getIPById(Long id) {
        InternshipPhases internshipPhases = IPR.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.internship_phase.not_found"))
        );

        return internshipPhaseMapper.internshipPhaseToResponseDTO(internshipPhases);
    }

    @Override
    public InternshipPhaseResponseDTO createIP(InternshipPhaseRequestDTO internshipPhaseRequestDTO) {
        InternshipPhases internshipPhases = internshipPhaseMapper.requestToInternshipPhase(internshipPhaseRequestDTO);
        InternshipPhases newInternshipPhase = IPR.save(internshipPhases);
        return internshipPhaseMapper.internshipPhaseToResponseDTO(newInternshipPhase);
    }

    @Override
    public InternshipPhaseResponseDTO updateIP(InternshipPhaseRequestDTO internshipPhaseRequestDTO , Long id) {
        InternshipPhases internshipPhases = IPR.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.internship_phase.not_found"))
        );
        internshipPhaseMapper.updateInternshipPhase(internshipPhaseRequestDTO , internshipPhases);
        InternshipPhases updateIP = IPR.save(internshipPhases);
        return internshipPhaseMapper.internshipPhaseToResponseDTO(updateIP);
    }

    @Override
    public void deleteIP(Long id) {
        InternshipPhases internshipPhases = IPR.findById(id).orElseThrow(() ->
            new ResourceNotFoundException(ResponseWrapper.getMessage("error.internship_phase.not_found"))
        );
        IPR.delete(internshipPhases);
    }
}
