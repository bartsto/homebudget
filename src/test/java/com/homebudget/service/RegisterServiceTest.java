package com.homebudget.service;

import com.homebudget.dto.RechargeRegisterRequest;
import com.homebudget.dto.RegisterSummaryResponse;
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
import java.util.Arrays;
import java.util.List;
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

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(registerRepository.findById(Mockito.any())).thenReturn(Optional.of(register));
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

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(registerRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> registerService.recharge(request)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void getSummaryShouldReturnListOfRegisters() {
        Register walletRegister = new Register();
        walletRegister.setName("Wallet");
        walletRegister.setBalance(3000);
        Register savingsRegister = new Register();
        savingsRegister.setName("Savings");
        savingsRegister.setBalance(500);
        List<Register> listOfRegisters = Arrays.asList(walletRegister, savingsRegister);
        User user = new User("John", listOfRegisters);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(registerRepository.findAllByUserId(Mockito.any())).thenReturn(listOfRegisters);

        RegisterSummaryResponse response = registerService.getSummary(1L);

        Assertions.assertThat(response.getListOfRegisters().size()).isEqualTo(listOfRegisters.size());
    }

    @Test
    public void getSummaryShouldReturnExceptionWhenUserNotFound() {
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> registerService.getSummary(10L)).isInstanceOf(EntityNotFoundException.class);
    }
}
