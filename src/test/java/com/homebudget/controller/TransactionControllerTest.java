package com.homebudget.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebudget.dto.TransactionTransferRequest;
import com.homebudget.model.Transaction;
import com.homebudget.service.TransactionService;
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
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @MockBean
    TransactionService transactionService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void transferShouldReturnOk() throws Exception {
        TransactionTransferRequest request = new TransactionTransferRequest();
        Transaction transaction = new Transaction();

        Mockito.when(transactionService.transfer(Mockito.any(TransactionTransferRequest.class))).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/transaction/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    public void transferShouldReturnNotFound() throws Exception {
        TransactionTransferRequest request = new TransactionTransferRequest();

        Mockito.when(transactionService.transfer(Mockito.any(TransactionTransferRequest.class))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.post("/register/recharge")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
