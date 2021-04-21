package com.homebudget.controller;

import com.homebudget.dto.TransactionTransferRequest;
import com.homebudget.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity transfer(@RequestBody TransactionTransferRequest transactionTransferRequest) {
        transactionService.transfer(transactionTransferRequest);
        return ResponseEntity.ok().build();
    }
}
