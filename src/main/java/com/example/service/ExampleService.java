package com.example.service;

import com.example.model.Example;

import java.util.List;
import java.util.Optional;

public interface ExampleService {

    List<Example> getAll();

    Optional<Example> getById(Long id);

    void deleteById(Long id);

    Example add(Example added);

    Example update(Long id, Example updated);
}
