package org.ra.qltt.model.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private String username;
    private String fullName;
    private String type;
    private String token;
    private String role;
}
