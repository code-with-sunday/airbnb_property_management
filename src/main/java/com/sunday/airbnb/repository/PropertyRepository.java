package com.sunday.airbnb.repository;


import com.sunday.airbnb.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Page<Property> findByIdOrName(Long userId, String name, Pageable pageable);
    Optional<Property> findById(Long id);
}