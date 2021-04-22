package com.homebudget.service;

import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.dto.RegisterSummaryResponse;
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

    public Register recharge(RechargeRegisterRequest rechargeRegisterRequest) {
        userRepository.findById(rechargeRegisterRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %s not found", rechargeRegisterRequest.getUserId())));
        Register register = registerRepository.findById(rechargeRegisterRequest.getRegisterId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Register with id: %s not found", rechargeRegisterRequest.getRegisterId())));
        register.setBalance(register.getBalance() + rechargeRegisterRequest.getAmount());

        return registerRepository.save(register);
    }

    public RegisterSummaryResponse getSummary(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %s not found", userId)));
        RegisterSummaryResponse response = new RegisterSummaryResponse();
        response.setListOfRegisters(registerRepository.findAllByUserId(userId));
        return response;
    }
}
