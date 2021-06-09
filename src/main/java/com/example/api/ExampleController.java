package com.example.api;

import com.example.model.Example;
import com.example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping("/examples")
    List<Example> getAll() {
        return exampleService.getAll();
    }

    @GetMapping("/example/{exampleId}")
    Example get(@PathVariable Long exampleId) {
        return exampleService.getById(exampleId).orElse(null);
    }

    @PostMapping("/examples")
    Example create(@Valid @RequestBody Example example) {
        return exampleService.add(example);
    }

    @PutMapping("/example/{exampleId}")
    Example update(@PathVariable Long exampleId, @Valid @RequestBody Example example) {
        return exampleService.update(exampleId, example);
    }

    @DeleteMapping("/example/{exampleId}")
    void delete(@PathVariable Long exampleId) {
        exampleService.deleteById(exampleId);
    }
}
