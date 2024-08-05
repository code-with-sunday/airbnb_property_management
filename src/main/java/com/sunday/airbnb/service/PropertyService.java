package com.sunday.airbnb.service;


import com.sunday.airbnb.DTO.request.PropertyDTO;

import java.util.List;

public interface PropertyService {
    PropertyDTO createProperty(PropertyDTO propertyDTO);
    List<PropertyDTO> getAllProperties(Integer pageNumber, Integer pageSize, String sortBy, String order);
    PropertyDTO getPropertyById(Long id);
    PropertyDTO updateProperty(Long id, PropertyDTO propertyDetails);
    String deleteProperty(Long id);
    List<PropertyDTO> getPropertiesByUserIdOrName(Long userId, String name, Integer pageNumber, Integer pageSize, String sortBy, String order);
}

