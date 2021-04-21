package com.homebudget.controller;

import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
