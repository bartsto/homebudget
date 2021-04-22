package com.homebudget.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.homebudget.views.Views;
import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/recharge")
    public ResponseEntity recharge(@RequestBody RechargeRegisterRequest rechargeRegisterRequest) {
        registerService.recharge(rechargeRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/summary/{userId}")
    @JsonView(Views.Summary.class)
    public ResponseEntity summary(@PathVariable Long userId) {
        return ResponseEntity.ok(registerService.getSummary(userId));
    }
}
