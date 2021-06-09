package com.example.service.jpa.repository;

import com.example.service.jpa.mapping.ExampleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<ExampleMapping, Long> {

}
