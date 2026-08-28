package org.ra.qltt.service.impl;

import lombok.RequiredArgsConstructor;
import org.ra.qltt.exception.ResourceNotFoundException;
import org.ra.qltt.exception.ResponseWrapper;
import org.ra.qltt.model.dto.enums.UserRole;
import org.ra.qltt.model.dto.request.MentorRequestDTO;
import org.ra.qltt.model.dto.response.MentorResponseDTO;
import org.ra.qltt.model.entity.Mentors;
import org.ra.qltt.model.entity.Users;
import org.ra.qltt.model.mapper.MentorMapper;
import org.ra.qltt.repository.MentorRepository;
import org.ra.qltt.repository.UserRepository;
import org.ra.qltt.security.UserPrinciple;
import org.ra.qltt.service.MentorService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MentorServiceImpl implements MentorService {

    private final MentorRepository mentorRepository;
    private final MentorMapper mentorMapper;
    private final UserRepository  userRepository;

    @Override
    public List<MentorResponseDTO> getMentor() {
        List<Mentors> mentors = mentorRepository.findAll();
        return mentorMapper.mentorsToMentorResponseDTOs(mentors);
    }

    @Override
    public MentorResponseDTO findMentorByID(Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        Users users = principal.getUser();

        assert users != null;
        Mentors mentor = mentorRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ResponseWrapper.getMessage("error.mentor.not_found"))
        );
        if ("MENTOR".equals(users.getRole()) && users.getId() != id) {
            throw new AccessDeniedException(
                    "Chỉ xem được thông tin bản thân"
            );
        }
        return mentorMapper.mentorToMentorResponseDTO(  mentor);
    }

    @Override
    public MentorResponseDTO createMentor(MentorRequestDTO mentorRequestDTO) {
        Users user = userRepository.findById(mentorRequestDTO.getUserId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(ResponseWrapper.getMessage("error.mentor.not_found"))
                );

        if (!user.getRole().equals(UserRole.MENTOR.name())) {
            throw new IllegalArgumentException(
                    "User phải có role MENTOR"
            );
        }

        if (mentorRepository.existsById(user.getId())) {
            throw new IllegalArgumentException(
                    "User này đã có thông tin "
            );
        }

        Mentors mentor =
                mentorMapper.mentorRequestToMentors(mentorRequestDTO);

        mentor.setUser(user);

        Mentors mentors =
                mentorRepository.save(mentor);

        return mentorMapper.mentorToMentorResponseDTO(mentors);
    }

    @Override
    public MentorResponseDTO updateMentor(MentorRequestDTO mentorRequestDTO , Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        UserPrinciple principal =
                (UserPrinciple) authentication.getPrincipal();

        assert principal != null;
        String username = principal.getUser().getUsername();

        Mentors mentors = mentorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy giáo viên với ID: " + id
                ));

        Users currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy người dùng: " + username
                ));


        if (currentUser.getRole().equals(UserRole.MENTOR.name())
                && !mentors.getId().equals(currentUser.getId())) {

            throw new AccessDeniedException(
                    "Chỉ được cập nhật thông tin của chính mình"
            );
        }

        if (mentorRequestDTO.getUserId() != null
                && !mentorRequestDTO.getUserId().equals(mentors.getId())) {

            throw new IllegalArgumentException(
                    "Không được thay đổi người dùng liên kết với giáo viên"
            );
        }

        mentorMapper.updateMentorFromDTO(mentorRequestDTO, mentors);

        Mentors updatedMentor = mentorRepository.save(mentors);

        return mentorMapper.mentorToMentorResponseDTO(updatedMentor);
    }
}
