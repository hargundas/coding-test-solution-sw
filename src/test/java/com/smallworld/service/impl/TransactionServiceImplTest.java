package com.smallworld.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceImplTest {

    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionServiceImpl();
    }
    @Test
    void testGetAllTransactionWithSomeTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);
        transaction1.setSenderFullName("SenderA");
        transaction1.setBeneficiaryFullName("BeneficiaryA");

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200.0);
        transaction2.setSenderFullName("SenderB");
        transaction2.setBeneficiaryFullName("BeneficiaryB");


        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction1);
        transactions.add(transaction2);

        transactionService.setTransactions(transactions);

        List<Transaction> result = transactionService.getAllTransaction();
        assertEquals(2, result.size());
        assertEquals(transaction1, result.get(0));
        assertEquals(transaction2, result.get(1));
    }
    @Test
    void testGetAllTransactionWithEmptyList() {
        List<Transaction> transactions = Collections.emptyList();
        transactionService.setTransactions(transactions);

        List<Transaction> result = transactionService.getAllTransaction();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetTotalTransactionAmountWithNoTransactions() {
        double totalAmount = transactionService.getTotalTransactionAmount();
        assertEquals(0.0, totalAmount);
    }

    @Test
    void testGetTotalTransactionAmountSentByWithNoSender() {
        double totalAmountSentBySender = transactionService.getTotalTransactionAmountSentBy("NonExistentSender");
        assertEquals(0.0, totalAmountSentBySender);
    }

    @Test
    void testGetMaxTransactionAmountWithNoTransactions() {
        double maxAmount = transactionService.getMaxTransactionAmount();
        assertEquals(0.0, maxAmount);
    }

    @Test
    void testGetTop3TransactionsByAmountWithFewerThan3Transactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);

        List<Transaction> transactions = Collections.singletonList(transaction1);
        transactionService.setTransactions(transactions);

        List<Transaction> topTransactions = transactionService.getTop3TransactionsByAmount();
        assertEquals(1, topTransactions.size());
        assertEquals(100.0, topTransactions.get(0).getAmount());
    }

    @Test
    void testGetTop3TransactionsByAmountWithNoTransactions() {
        List<Transaction> topTransactions = transactionService.getTop3TransactionsByAmount();
        assertTrue(topTransactions.isEmpty());
    }
}
