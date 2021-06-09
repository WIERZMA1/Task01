package com.example.service.jpa.service;

import com.example.model.Example;
import com.example.service.ExampleService;
import com.example.service.jpa.mapping.ExampleMapping;
import com.example.service.jpa.repository.ExampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class JpaExampleService implements ExampleService {

    @Autowired
    ExampleRepository exampleRepository;

    @Override
    public List<Example> getAll() {
        return exampleRepository.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<Example> getById(Long id) {
        return exampleRepository.findById(id).map(this::toModel);
    }

    @Override
    public void deleteById(Long id) {
        exampleRepository.deleteById(id);
    }

    @Override
    public Example add(Example added) {
        return toModel(exampleRepository.saveAndFlush(toMapping(added)));
    }

    @Override
    public Example update(Long id, Example updated) {
        log.info(id.toString());
        log.info(updated.toString());
        updated.setId(id);
        log.info(updated.toString());
        log.info("EXISTS BY ID: " + exampleRepository.existsById(id));
        return exampleRepository.existsById(id) ? toModel(exampleRepository.saveAndFlush(toMapping(updated))) : null;
    }

    private Example toModel(ExampleMapping mapping) {
        Example example = new Example();
        BeanUtils.copyProperties(mapping, example);
        return example;
    }

    private ExampleMapping toMapping (Example example) {
        ExampleMapping mapping = new ExampleMapping();
        BeanUtils.copyProperties(example, mapping);
        return mapping;
    }
}
