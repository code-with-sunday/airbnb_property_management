package com.sunday.airbnb.DTO.request;

import com.sunday.airbnb.enums.ROLE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.ROLE_USER;

}
