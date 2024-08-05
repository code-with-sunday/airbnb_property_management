package com.sunday.airbnb.mapper;

import com.sunday.airbnb.DTO.request.PropertyDTO;
import com.sunday.airbnb.model.Property;
import com.sunday.airbnb.model.User;
import com.sunday.airbnb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyMapper {
    private final UserRepository userRepository;

    public PropertyDTO mapToDTO(Property property) {

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setName(property.getName());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setPricePerNight(property.getPricePerNight());
        propertyDTO.setNumberOfBedrooms(property.getNumberOfBedrooms());
        propertyDTO.setNumberOfBathrooms(property.getNumberOfBathrooms());
        propertyDTO.setAvailable(property.isAvailable());
        propertyDTO.setDrinkAllowed(property.isDrinkAllowed());
        propertyDTO.setPetAllowed(property.isPetAllowed());
        propertyDTO.setMaxCheckoutTimeInNights(property.getMaxCheckoutTimeInNights());
        propertyDTO.setExtraCharges(property.getExtraCharges());
        propertyDTO.setUserId(property.getUser().getId());
        return propertyDTO;
    }

    public Property mapToEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setName(propertyDTO.getName());
        property.setDescription(propertyDTO.getDescription());
        property.setAddress(propertyDTO.getAddress());
        property.setPricePerNight(propertyDTO.getPricePerNight());
        property.setNumberOfBedrooms(propertyDTO.getNumberOfBedrooms());
        property.setNumberOfBathrooms(propertyDTO.getNumberOfBathrooms());
        property.setAvailable(propertyDTO.isAvailable());
        property.setDrinkAllowed(propertyDTO.isDrinkAllowed());
        property.setPetAllowed(propertyDTO.isPetAllowed());
        property.setMaxCheckoutTimeInNights(propertyDTO.getMaxCheckoutTimeInNights());
        property.setExtraCharges(propertyDTO.getExtraCharges());
        User user = userRepository.findById(propertyDTO.getUserId()).orElse(null);
        property.setUser(user);
        return property;
    }
}
