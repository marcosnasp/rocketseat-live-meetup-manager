package com.demo.meetupmanager.controller;

import com.demo.meetupmanager.controller.dto.RegistrationDTO;
import com.demo.meetupmanager.controller.web.RegistrationController;
import com.demo.meetupmanager.model.Registration;
import com.demo.meetupmanager.service.RegistrationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = {RegistrationController.class})
@AutoConfigureMockMvc
public class RegistrationControllerTest {


    static String REGISTRATION_API = "/api/registration";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegistrationService registrationService;

    @Test
    @DisplayName("Should create a registration with success")
    public void createRegistrationTest() throws Exception {

        // cenario
        RegistrationDTO registrationDTOBuilder = createNewRegistration();
        Registration savedRegistration = Registration.builder().id(101)
                .name("Ana Neri").dateOfRegistration("10/10/2021").registration("001").build();


        // execucao
        BDDMockito.given(registrationService.save(any(Registration.class))).willReturn(savedRegistration);


        String json  = new ObjectMapper().writeValueAsString(registrationDTOBuilder);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(REGISTRATION_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // verificacao, assert....
        mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(101))
                .andExpect(jsonPath("name").value(registrationDTOBuilder.getName()))
                .andExpect(jsonPath("dateOfRegistration").value(registrationDTOBuilder.getDateOfRegistration()))
                .andExpect(jsonPath("registration").value(registrationDTOBuilder.getRegistration()));
    }


    private RegistrationDTO createNewRegistration() {
        return  RegistrationDTO.builder().id(101).name("Ana Neri").dateOfRegistration("10/10/2021").registration("001").build();
    }
}
