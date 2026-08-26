package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.config.MessageSourceConfig;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.model.dto.response.RoundCriterionResponseDTO;
import org.ra.qltt.model.entity.RoundCriteria;
import org.ra.qltt.model.mapper.RoundCriteriaMapper;
import org.ra.qltt.repository.RoundCriteriaRepository;
import org.ra.qltt.service.RoundCriteriaService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoundCriteriaServiceImpl implements RoundCriteriaService {
    private final RoundCriteriaRepository roundCriteriaRepository;
    private final RoundCriteriaMapper roundCriteriaMapper;
    private final MessageSourceConfig messageSourceConfig;

    @Override
    public List<RoundCriterionResponseDTO> getRC() {
        List<RoundCriteria> roundCriteria = roundCriteriaRepository.findAll();
        return roundCriteriaMapper.roundCriteriasToResponseDTOs(roundCriteria);
    }

    @Override
    public RoundCriterionResponseDTO getRCById(Long id) {
        RoundCriteria roundCriteria = roundCriteriaRepository.findById(id).orElseThrow(() -> {
            String errorMessage = messageSourceConfig.messageSource()
                    .getMessage("error.resource.not_found", new Object[]{"RoundCriteria", id}, LocaleContextHolder.getLocale());
            return new ResourceNotFoundException(errorMessage);
        });
        return roundCriteriaMapper.roundCriteriaToResponseDTO(roundCriteria);
    }
}
