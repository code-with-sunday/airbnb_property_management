package com.sunday.airbnb.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum InternalCodeEnum {
    PROPERTY_MANAGEMENT_CODE_001(1, "Request performed successfully", OK),
    PROPERTY_MANAGEMENT_CODE_002(2, "Property not found", NOT_FOUND),
    PROPERTY_MANAGEMENT_CODE_003(3, "Property created successfully", CREATED),
    PROPERTY_MANAGEMENT_CODE_004(4, "Property updated successfully", OK),
    PROPERTY_MANAGEMENT_CODE_005(5, "Property deleted successfully", OK),
    PROPERTY_MANAGEMENT_CODE_006(6, "User properties retrieved successfully", OK);

    private final String codeDescription;
    private final String codeNumber;
    private final HttpStatus httpStatus;

    InternalCodeEnum(int codeNumber, String codeDescription, HttpStatus status) {
        this.codeNumber = String.format("%03d", codeNumber);
        this.codeDescription = codeDescription;
        this.httpStatus = status;
    }
}
