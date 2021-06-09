package com.example.api;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestExecutionListeners(
        listeners = {DbUnitTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@DatabaseTearDown("classpath:com/example/clearData.xml")
public class ExampleControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void getAll() throws Exception {
        mockMvc.perform(get("/api/examples"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(5)));
    }

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void getById() throws Exception {
        mockMvc.perform(get("/api/example/{exampleId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("example01")));
    }

    @Test
    public void getNonExisting() throws Exception {
        mockMvc.perform(get("/api/example/{exampleId}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/api/examples")
                .content("{ \"id\":\"1\", \"description\":\"New Example\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.description", is("New Example")));
    }

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void update() throws Exception {
        mockMvc.perform(put("/api/example/3")
                .content("{ \"id\":\"3\", \"description\":\"Changed Example\" }")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.description", is("Changed Example")));
    }

    @Test
    @DatabaseSetup("classpath:com/example/examplesData.xml")
    public void deleteById() throws Exception {
        mockMvc.perform(delete("/api/example/3")).andExpect(status().isOk());
    }
}