package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.model.dto.request.InternshipPhaseRequestDTO;
import org.ra.qltt.model.dto.response.InternshipPhaseResponseDTO;
import org.ra.qltt.model.entity.InternshipPhases;
import org.ra.qltt.model.mapper.InternshipPhaseMapper;
import org.ra.qltt.repository.InternshipPhasesRepository;
import org.ra.qltt.service.InternshipPhasesService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipPhasesServiceImpl implements InternshipPhasesService {

    private final InternshipPhasesRepository IPR;
    private final InternshipPhaseMapper internshipPhaseMapper;
    private final MessageSourceConfig messageSourceConfig;

    @Override
    public List<InternshipPhaseResponseDTO> getIP() {
        List<InternshipPhases> IP = IPR.findAll();
        return internshipPhaseMapper.internshipPhasesToResponseDTOs(IP);
    }

    @Override
    public InternshipPhaseResponseDTO getIPById(Long id) {
        InternshipPhases internshipPhases = IPR.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"InternshipPhases", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });

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
        InternshipPhases internshipPhases = IPR.findById(id).orElseThrow(() ->{
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"InternshipPhases", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        internshipPhaseMapper.updateInternshipPhase(internshipPhaseRequestDTO , internshipPhases);
        InternshipPhases updateIP = IPR.save(internshipPhases);
        return internshipPhaseMapper.internshipPhaseToResponseDTO(updateIP);
    }

    @Override
    public void deleteIP(Long id) {
        InternshipPhases internshipPhases = IPR.findById(id).orElseThrow(() ->{
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"InternshipPhases", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        IPR.delete(internshipPhases);
    }
}
