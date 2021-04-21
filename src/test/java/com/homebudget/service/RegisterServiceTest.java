package com.homebudget.service;

import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.model.Register;
import com.homebudget.model.User;
import com.homebudget.repository.RegisterRepository;
import com.homebudget.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServiceTest {

    @Mock
    RegisterRepository registerRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    RegisterService registerService;

    @Test
    public void rechargeShouldReturnRegisterWithCorrectBalance() {
        RechargeRegisterRequest request = new RechargeRegisterRequest();
        request.setAmount(2000);
        Register register = new Register();
        register.setBalance(0);
        User user = new User();
        user.setUsername("John");

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(registerRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(register));
        Mockito.when(registerRepository.save(Mockito.any())).thenReturn(register);

        Register returnedRegister = registerService.recharge(request);

        Assertions.assertThat(returnedRegister.getBalance()).isEqualTo(request.getAmount());
    }

    @Test
    public void rechargeShouldReturnExceptionWhenRegisterNotFound() {
        RechargeRegisterRequest request = new RechargeRegisterRequest();
        request.setAmount(2000);
        Register register = new Register();
        register.setBalance(0);
        User user = new User();
        user.setUsername("John");

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(java.util.Optional.of(user));
        Mockito.when(registerRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> registerService.recharge(request)).isInstanceOf(EntityNotFoundException.class);
    }
}
