package com.sunday.airbnb.DTO.response;

import com.sunday.airbnb.enums.ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String Title;
    private String message;
    private ROLE role;
}
