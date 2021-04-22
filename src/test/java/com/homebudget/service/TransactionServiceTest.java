package com.homebudget.service;

import com.homebudget.dto.TransactionTransferRequest;
import com.homebudget.model.Register;
import com.homebudget.model.Transaction;
import com.homebudget.model.TransactionStatus;
import com.homebudget.model.User;
import com.homebudget.repository.RegisterRepository;
import com.homebudget.repository.TransactionRepository;
import com.homebudget.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    RegisterRepository registerRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TransactionService transactionService;

    @Test
    public void transferShouldMoveMoneyFromOneRegisterToAnother() {
        TransactionTransferRequest request = new TransactionTransferRequest(1, 1, 2, 1000);
        Register sourceRegister = new Register();
        sourceRegister.setId(1);
        sourceRegister.setBalance(3000);
        Register destinationRegister = new Register();
        destinationRegister.setId(2);
        destinationRegister.setBalance(0);
        User user = new User("John", Arrays.asList(sourceRegister, destinationRegister));
        Transaction transaction = new Transaction(1000, LocalDateTime.now(), TransactionStatus.SUCCESS, sourceRegister, destinationRegister);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(registerRepository.findById(sourceRegister.getId())).thenReturn(Optional.of(sourceRegister));
        Mockito.when(registerRepository.findById(destinationRegister.getId())).thenReturn(Optional.of(destinationRegister));
        Mockito.when(registerRepository.save(sourceRegister)).thenReturn(sourceRegister);
        Mockito.when(registerRepository.save(destinationRegister)).thenReturn(destinationRegister);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(transaction);

        Transaction returnedTransaction = transactionService.transfer(request);

        Assertions.assertThat(returnedTransaction.getStatus()).isEqualTo(TransactionStatus.SUCCESS);
        Assertions.assertThat(sourceRegister.getBalance()).isEqualTo(2000);
        Assertions.assertThat(destinationRegister.getBalance()).isEqualTo(1000);
    }

    @Test
    public void transferShouldReturnExceptionWhenUserNotFound() {
        TransactionTransferRequest request = new TransactionTransferRequest(1, 1, 2, 1000);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> transactionService.transfer(request)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void transferShouldReturnExceptionWhenUserDoesNotHaveSourceOrDestinationRegister() {
        TransactionTransferRequest request = new TransactionTransferRequest(1, 1, 2, 1000);
        Register sourceRegister = new Register();
        sourceRegister.setId(10);
        Register destinationRegister = new Register();
        destinationRegister.setId(20);
        User user = new User("John", Arrays.asList(sourceRegister, destinationRegister));

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));

        Assertions.assertThatThrownBy(() -> transactionService.transfer(request)).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void transferShouldReturnExceptionWhenThereIsNotEnoughMoney() {
        TransactionTransferRequest request = new TransactionTransferRequest(1, 1, 2, 1000);
        Register sourceRegister = new Register();
        sourceRegister.setId(1);
        sourceRegister.setBalance(500);
        Register destinationRegister = new Register();
        destinationRegister.setId(2);
        destinationRegister.setBalance(0);
        User user = new User("John", Arrays.asList(sourceRegister, destinationRegister));

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(registerRepository.findById(sourceRegister.getId())).thenReturn(Optional.of(sourceRegister));
        Mockito.when(registerRepository.findById(destinationRegister.getId())).thenReturn(Optional.of(destinationRegister));

        Assertions.assertThatThrownBy(() -> transactionService.transfer(request)).isInstanceOf(ArithmeticException.class);
    }
}
