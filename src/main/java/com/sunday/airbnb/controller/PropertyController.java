package com.sunday.airbnb.controller;


import com.sunday.airbnb.DTO.ApiResponseBody;
import com.sunday.airbnb.DTO.request.PropertyDTO;
import com.sunday.airbnb.enums.AppConstants;
import com.sunday.airbnb.enums.InternalCodeEnum;
import com.sunday.airbnb.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<ApiResponseBody<PropertyDTO>> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO createdProperty = propertyService.createProperty(propertyDTO);
        return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_003.getHttpStatus())
                .body(new ApiResponseBody<>(true, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_003.getCodeDescription(), InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_003, createdProperty));
    }

    @GetMapping
    public ResponseEntity<ApiResponseBody<List<PropertyDTO>>> getAllProperties(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_ORDER, required = false) String order) {
        List<PropertyDTO> properties = propertyService.getAllProperties(pageNumber, pageSize, sortBy, order);
        return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_001.getHttpStatus())
                .body(new ApiResponseBody<>(
                        true, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_001.getCodeDescription(),
                        InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_001, properties)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseBody<PropertyDTO>> getPropertyById(@PathVariable Long id) {
        PropertyDTO property = propertyService.getPropertyById(id);
        if (property == null) {
            return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_002.getHttpStatus())
                    .body(new ApiResponseBody<>(
                            false, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_002.getCodeDescription(),
                            InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_002, null)
                    );
        }
        return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_001.getHttpStatus())
                .body(new ApiResponseBody<>(
                        true, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_001.getCodeDescription(),
                        InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_001, property)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseBody<PropertyDTO>> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO propertyDTO) {
        PropertyDTO updatedProperty = propertyService.updateProperty(id, propertyDTO);
        if (updatedProperty == null) {
            return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_002.getHttpStatus())
                    .body(new ApiResponseBody<>(
                            false, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_002.getCodeDescription(),
                            InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_002, null)
                    );
        }
        return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_004.getHttpStatus())
                .body(new ApiResponseBody<>(
                        true, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_004.getCodeDescription(),
                        InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_004, updatedProperty)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody<String>> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_005.getHttpStatus())
                .body(new ApiResponseBody<>(
                        true, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_005.getCodeDescription(),
                        InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_005, null)
                );
    }

    @GetMapping("/users/{userId}/properties")
    public ResponseEntity<ApiResponseBody<List<PropertyDTO>>> getPropertiesByUserId(
            @PathVariable Long userId,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_ORDER, required = false) String order) {
        List<PropertyDTO> properties = propertyService.getPropertiesByUserIdOrName(userId, name, pageNumber, pageSize, sortBy, order);
        return ResponseEntity.status(InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_006.getHttpStatus())
                .body(new ApiResponseBody<>(
                        true, InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_006.getCodeDescription(),
                        InternalCodeEnum.PROPERTY_MANAGEMENT_CODE_006, properties)
                );
    }
}
