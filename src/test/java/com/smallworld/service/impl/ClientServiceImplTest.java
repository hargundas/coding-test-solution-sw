package com.smallworld.service.impl;

import com.smallworld.model.Issue;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    @Mock
    private TransactionService transactionService;

    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(transactionService);
    }

    @Test
    void testCountUniqueClients() {
        Transaction transaction1 = new Transaction();
        transaction1.setSenderFullName("Sender1");
        transaction1.setBeneficiaryFullName("Beneficiary1");

        Transaction transaction2 = new Transaction();
        transaction2.setSenderFullName("Sender2");
        transaction2.setBeneficiaryFullName("Beneficiary1");

        Transaction transaction3 = new Transaction();
        transaction3.setSenderFullName("Sender3");
        transaction3.setBeneficiaryFullName("Beneficiary3");

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2, transaction3);

        when(transactionService.getAllTransaction()).thenReturn(transactions);

        long uniqueClients = clientService.countUniqueClients();
        assertEquals(3, uniqueClients);
    }

    @Test
    void testCountUniqueClientsWithDuplicateTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setSenderFullName("Sender1");
        transaction1.setBeneficiaryFullName("Beneficiary1");

        Transaction transaction2 = new Transaction();
        transaction2.setSenderFullName("Sender1");
        transaction2.setBeneficiaryFullName("Beneficiary1");

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.getAllTransaction()).thenReturn(transactions);

        long uniqueClients = clientService.countUniqueClients();
        assertEquals(1, uniqueClients);
    }

    @Test
    void testHasOpenComplianceIssuesWithNonExistingBeneficiary() {
        Transaction transaction1 = new Transaction();
        transaction1.setSenderFullName("Sender1");

        List<Transaction> transactions = List.of(transaction1);

        when(transactionService.getAllTransaction()).thenReturn(transactions);

        boolean hasOpenIssues = clientService.hasOpenComplianceIssues("NonExistingBeneficiary");
        assertFalse(hasOpenIssues);
    }


    @Test
    void testHasOpenComplianceIssues() {
        Transaction transaction1 = new Transaction();
        transaction1.setSenderFullName("Sender1");

        Transaction transaction2 = new Transaction();
        transaction2.setBeneficiaryFullName("Beneficiary1");
        transaction2.setIssues(List.of(new Issue(1L, false, "issueTest")));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.getAllTransaction()).thenReturn(transactions);

        boolean hasOpenIssues = clientService.hasOpenComplianceIssues("Beneficiary1");

        assertTrue(hasOpenIssues);
    }

    @Test
    void testGetTopSender() {
        Transaction transaction1 = new Transaction();
        transaction1.setSenderFullName("Sender1");
        transaction1.setAmount(100.0);

        Transaction transaction2 = new Transaction();
        transaction2.setSenderFullName("Sender2");
        transaction2.setAmount(200.0);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.getAllTransaction()).thenReturn(transactions);

        Optional<String> topSender = clientService.getTopSender();

        assertTrue(topSender.isPresent());
        assertEquals("Sender2", topSender.get());
    }

    @Test
    void testGetTopSenderWithNegativeAmounts() {
        Transaction transaction1 = new Transaction();
        transaction1.setSenderFullName("Sender1");
        transaction1.setAmount(-100.0);

        Transaction transaction2 = new Transaction();
        transaction2.setSenderFullName("Sender2");
        transaction2.setAmount(-200.0);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionService.getAllTransaction()).thenReturn(transactions);

        Optional<String> topSender = clientService.getTopSender();
        assertTrue(topSender.isPresent());
    }

}
