package com.smallworld.service.impl;

import com.smallworld.model.Transaction;
import com.smallworld.service.ClientService;
import com.smallworld.service.TransactionService;
import com.smallworld.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private final TransactionService transactionService;

    @Autowired
    public ClientServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public long countUniqueClients() {
        List<Transaction> transactions = transactionService.getAllTransaction();

        return transactions.stream()
                .map(Transaction::getSenderFullName)
                .distinct()
                .count();
    }

    @Override
    public boolean hasOpenComplianceIssues(String clientFullName) {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.stream()
                .anyMatch(transaction -> TransactionUtil.hasOpenIssue(transaction, clientFullName));
    }

    @Override
    public Optional<String> getTopSender() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}
