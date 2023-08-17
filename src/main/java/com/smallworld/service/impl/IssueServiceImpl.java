package com.smallworld.service.impl;

import com.smallworld.model.Issue;
import com.smallworld.model.Transaction;
import com.smallworld.service.IssueService;
import com.smallworld.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IssueServiceImpl implements IssueService {

    private final TransactionService transactionService;

    @Autowired
    public IssueServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Set<Long> getUnsolvedIssueIds() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.stream()
                .flatMap(transaction -> transaction.getIssues().stream()
                        .filter(issue -> issue.getIssueId() != null && !issue.getIssueSolved())
                        .map(Issue::getIssueId))
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> getAllSolvedIssueMessages() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.stream()
                .flatMap(transaction -> transaction.getIssues().stream()
                        .filter(issue -> issue.getIssueId() != null && issue.getIssueSolved())
                        .map(Issue::getIssueMessage)).toList();
    }
}
