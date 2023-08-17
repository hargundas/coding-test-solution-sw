package com.smallworld.service.impl;

import com.smallworld.model.Issue;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class IssueServiceImplTest {

    @Mock
    private TransactionService transactionService;

    private IssueServiceImpl issueService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        issueService = new IssueServiceImpl(transactionService);
    }

    @Test
    void testGetUnsolvedIssueIdsWithNoIssues() {
        Transaction transaction = new Transaction();
        transaction.setIssues(Collections.emptyList());

        when(transactionService.getAllTransaction()).thenReturn(Collections.singletonList(transaction));

        Set<Long> unsolvedIssueIds = issueService.getUnsolvedIssueIds();
        assertTrue(unsolvedIssueIds.isEmpty());
    }

    @Test
    void testGetUnsolvedIssueIdsWithSolvedIssues() {
        Transaction transaction = new Transaction();
        transaction.setIssues(List.of(new Issue(1L, true, "solvedIssue")));

        when(transactionService.getAllTransaction()).thenReturn(Collections.singletonList(transaction));

        Set<Long> unsolvedIssueIds = issueService.getUnsolvedIssueIds();
        assertTrue(unsolvedIssueIds.isEmpty());
    }

    @Test
    void testGetAllSolvedIssueMessagesWithNoIssues() {
        Transaction transaction = new Transaction();
        transaction.setIssues(Collections.emptyList());

        when(transactionService.getAllTransaction()).thenReturn(Collections.singletonList(transaction));

        List<String> solvedIssueMessages = issueService.getAllSolvedIssueMessages();
        assertTrue(solvedIssueMessages.isEmpty());
    }

    @Test
    void testGetAllSolvedIssueMessagesWithUnsolvedIssues() {
        Transaction transaction = new Transaction();
        transaction.setIssues(List.of(new Issue(1L, false, "unsolvedIssue")));

        when(transactionService.getAllTransaction()).thenReturn(Collections.singletonList(transaction));

        List<String> solvedIssueMessages = issueService.getAllSolvedIssueMessages();
        assertTrue(solvedIssueMessages.isEmpty());
    }

    @Test
    void testGetUnsolvedIssueIdsWithMultipleTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setIssues(List.of(new Issue(1L, false, "unsolvedIssue")));

        Transaction transaction2 = new Transaction();
        transaction2.setIssues(List.of(new Issue(2L, true, "solvedIssue")));

        when(transactionService.getAllTransaction()).thenReturn(Arrays.asList(transaction1, transaction2));

        Set<Long> unsolvedIssueIds = issueService.getUnsolvedIssueIds();
        assertEquals(Set.of(1L), unsolvedIssueIds);
    }

    @Test
    void testGetAllSolvedIssueMessagesWithMultipleTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setIssues(List.of(new Issue(1L, true, "solvedIssue1")));

        Transaction transaction2 = new Transaction();
        transaction2.setIssues(List.of(new Issue(2L, true, "solvedIssue2")));

        when(transactionService.getAllTransaction()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<String> solvedIssueMessages = issueService.getAllSolvedIssueMessages();
        assertEquals(List.of("solvedIssue1", "solvedIssue2"), solvedIssueMessages);
    }
}
