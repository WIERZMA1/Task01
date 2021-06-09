package com.example.service.jpa.service;

import com.example.model.Example;
import com.example.service.ExampleService;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestExecutionListeners(
        listeners = {DbUnitTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@DatabaseTearDown("classpath:com/example/clearData.xml")
public class ExampleServiceTest {

    @Autowired
    public ExampleService service;

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void getAll() {
        List<Example> allExamples = service.getAll();
        assertNotNull(allExamples);
        assertEquals(5, allExamples.size());
    }

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void getById() {
        Example example = service.getById(1L).orElse(null);
        assertNotNull(example);
        assertEquals(1L, example.getId());
        assertEquals("example01", example.getDescription());
    }

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void deleteById() {
        service.deleteById(3L);
        assertEquals(4, service.getAll().size());
    }

    @Test
    public void add() {
        Example added = service.add(new Example(1L, "New Example"));
        assertNotNull(added);
        assertEquals(1, service.getAll().size());
    }

    @Test
    public void addNonValid() {
        assertThrows(ConstraintViolationException.class, () -> service.add(
                new Example(1L, "New Example With Definitely Too Long Description")));
        assertEquals(0, service.getAll().size());
    }

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void update() {
        Example updated = service.update(1L, new Example(1L, "New Description"));
        assertNotNull(updated);
        assertEquals("New Description", service.getById(1L).get().getDescription());
    }
}