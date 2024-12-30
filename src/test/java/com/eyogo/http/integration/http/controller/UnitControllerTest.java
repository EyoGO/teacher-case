package com.eyogo.http.integration.http.controller;

import com.eyogo.http.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UnitControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    void findAll() throws Exception {
        mockMvc.perform(get("/units"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("unit"))
                .andExpect(model().attributeExists("units", "usersSelection"))
                .andExpect(model().attribute("units", hasSize(7)))
                .andExpect(model().attribute("usersSelection", hasSize(2)));
    }
}
