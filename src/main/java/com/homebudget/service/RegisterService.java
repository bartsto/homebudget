package com.homebudget.service;

import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.model.Register;
import com.homebudget.repository.RegisterRepository;
import com.homebudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private UserRepository userRepository;

    public void recharge(RechargeRegisterRequest rechargeRegisterRequest) {
        userRepository.findById(rechargeRegisterRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Register register = registerRepository.findById(rechargeRegisterRequest.getRegisterId()).orElseThrow(() -> new EntityNotFoundException("Register not found"));
        register.setBalance(register.getBalance() + rechargeRegisterRequest.getAmount());

        registerRepository.save(register);
    }
}
