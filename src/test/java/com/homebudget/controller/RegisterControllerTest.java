package com.homebudget.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.dto.RegisterSummaryResponse;
import com.homebudget.model.Register;
import com.homebudget.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityNotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {

    @MockBean
    RegisterService registerService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rechargeShouldReturnOk() throws Exception {
        RechargeRegisterRequest request = new RechargeRegisterRequest();
        Register register = new Register();

        Mockito.when(registerService.recharge(Mockito.any(RechargeRegisterRequest.class))).thenReturn(register);

        mockMvc.perform(MockMvcRequestBuilders.post("/register/recharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void rechargeShouldReturnNotFound() throws Exception {
        RechargeRegisterRequest request = new RechargeRegisterRequest();

        Mockito.when(registerService.recharge(Mockito.any(RechargeRegisterRequest.class))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.post("/register/recharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void summaryShouldReturnOk() throws Exception {
        RegisterSummaryResponse response = new RegisterSummaryResponse();

        Mockito.when(registerService.getSummary(Mockito.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/register/summary/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    public void summaryShouldReturnNotFound() throws Exception {
        Mockito.when(registerService.getSummary(Mockito.any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.post("/register/summary/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
