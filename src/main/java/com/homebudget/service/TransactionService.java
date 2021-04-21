package com.homebudget.service;

import com.homebudget.dto.TransactionTransferRequest;
import com.homebudget.model.Register;
import com.homebudget.model.Transaction;
import com.homebudget.model.TransactionStatus;
import com.homebudget.model.User;
import com.homebudget.repository.RegisterRepository;
import com.homebudget.repository.TransactionRepository;
import com.homebudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transaction transfer(TransactionTransferRequest transactionTransferRequest) {
        verifyUserPreConditions(transactionTransferRequest);

        if(transactionTransferRequest.getSourceRegisterId() == transactionTransferRequest.getDestinationRegisterId()) {
            throw new UnsupportedOperationException("Source and destination can't be the same");
        }

        Register sourceRegister = registerRepository.findById(transactionTransferRequest.getSourceRegisterId())
                .orElseThrow(() -> new EntityNotFoundException("Source register not found"));
        Register destinationRegister = registerRepository.findById(transactionTransferRequest.getDestinationRegisterId())
                .orElseThrow(() -> new EntityNotFoundException("Destination register not found"));

        if(sourceRegister.getBalance() < transactionTransferRequest.getAmount()) {
            throw new ArithmeticException("Not enough money");
        }

        Transaction transaction = new Transaction();
        transaction.setSource(sourceRegister);
        transaction.setDestination(destinationRegister);
        transaction.setAmount(transactionTransferRequest.getAmount());

        sourceRegister.setBalance(sourceRegister.getBalance() - transaction.getAmount());
        registerRepository.save(sourceRegister);

        destinationRegister.setBalance(destinationRegister.getBalance() + transaction.getAmount());
        registerRepository.save(destinationRegister);

        transaction.setStatus(TransactionStatus.SUCCESS);

        return transactionRepository.save(transaction);
    }

    private void verifyUserPreConditions(TransactionTransferRequest transactionTransferRequest) {
        User user = userRepository.findById(transactionTransferRequest.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if(!userContainsTransactionRegisters(user.getRegisters(), transactionTransferRequest.getSourceRegisterId())) {
            throw new UnsupportedOperationException("Source register doesn't exist for this user");
        }

        if(!userContainsTransactionRegisters(user.getRegisters(), transactionTransferRequest.getDestinationRegisterId())) {
            throw new UnsupportedOperationException("Destination register doesn't exist for this user");
        }
    }

    private boolean userContainsTransactionRegisters(List<Register> list, Long transactionRegister) {
        return list.stream().anyMatch(o -> o.getId() == (transactionRegister));
    }
}
