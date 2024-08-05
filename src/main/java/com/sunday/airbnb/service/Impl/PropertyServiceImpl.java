package com.sunday.airbnb.service.Impl;

import com.sunday.airbnb.DTO.request.PropertyDTO;
import com.sunday.airbnb.exception.PropertyNotFoundException;
import com.sunday.airbnb.mapper.PropertyMapper;
import com.sunday.airbnb.model.Property;
import com.sunday.airbnb.model.User;
import com.sunday.airbnb.repository.PropertyRepository;
import com.sunday.airbnb.repository.UserRepository;
import com.sunday.airbnb.service.PropertyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserRepository userRepository;

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = (String) authentication.getPrincipal();
            User user = userRepository.findByEmail(email);

            Property property = propertyMapper.mapToEntity(propertyDTO);
            property.setUser(user);
            Property savedProperty = propertyRepository.save(property);
            return propertyMapper.mapToDTO(savedProperty);
        }else {
            throw new RuntimeException("User not authenticated");
        }

    }

    public List<PropertyDTO> getAllProperties(Integer pageNumber, Integer pageSize, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<Property> properties = propertyRepository.findAll(pageable).getContent();
        return properties.stream().map(propertyMapper::mapToDTO).collect(Collectors.toList());
    }

    public PropertyDTO getPropertyById(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() ->
                new PropertyNotFoundException("Property not found with id: " + id));
        return property != null ? propertyMapper.mapToDTO(property) : null;
    }


    public PropertyDTO updateProperty(Long id, PropertyDTO propertyDetails) {
        Property property = propertyRepository.findById(id).orElseThrow(() ->
                new PropertyNotFoundException("Property not found with id: " + id));
        if (property != null) {
            property.setName(propertyDetails.getName());
            property.setDescription(propertyDetails.getDescription());
            property.setAddress(propertyDetails.getAddress());
            property.setPricePerNight(propertyDetails.getPricePerNight());
            property.setNumberOfBedrooms(propertyDetails.getNumberOfBedrooms());
            property.setNumberOfBathrooms(propertyDetails.getNumberOfBathrooms());
            property.setAvailable(propertyDetails.isAvailable());
            property.setDrinkAllowed(propertyDetails.isDrinkAllowed());
            property.setPetAllowed(propertyDetails.isPetAllowed());
            property.setMaxCheckoutTimeInNights(propertyDetails.getMaxCheckoutTimeInNights());
            property.setExtraCharges(propertyDetails.getExtraCharges());
            Property updatedProperty = propertyRepository.save(property);
            return propertyMapper.mapToDTO(updatedProperty);
        }
        return null;
    }

    public String deleteProperty(Long id) {
        Property property =  propertyRepository.findById(id).orElseThrow(() ->
                new PropertyNotFoundException("Property not found with id: " + id));
        propertyRepository.delete(property);
        return "Property deleted successfully with Id = " + id;
    }

    public List<PropertyDTO> getPropertiesByUserIdOrName(Long userId, String name, Integer pageNumber, Integer pageSize, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<Property> properties = propertyRepository.findByIdOrName(userId, name, pageable).getContent();
        return properties.stream().map(propertyMapper::mapToDTO).collect(Collectors.toList());
    }
}
