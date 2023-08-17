package com.smallworld;

import com.smallworld.model.Transaction;
import com.smallworld.model.Issue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionDataFetcher {

    private final List<Transaction> transactions;

    public TransactionDataFetcher(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getTotalTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return transactions.stream()
                .filter(transaction -> senderFullName.equals(transaction.getSenderFullName()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    public double getMaxTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .max()
                .orElse(0.0);
    }

    public long countUniqueClients() {
        return transactions.stream()
                .flatMap(transaction -> Stream.of(transaction.getSenderFullName(), transaction.getBeneficiaryFullName()))
                .distinct()
                .count();
    }

    public boolean hasOpenComplianceIssues(String clientFullName) {
        return transactions.stream()
                .anyMatch(transaction -> hasOpenIssue(transaction, clientFullName));
    }

    private boolean hasOpenIssue(Transaction transaction, String clientFullName) {
        return (clientFullName.equals(transaction.getSenderFullName()) || clientFullName.equals(transaction.getBeneficiaryFullName()))
                && transaction.getIssues().stream()
                .anyMatch(issue -> issue.getIssueId() != null && !issue.getIssueSolved());
    }

    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    public Set<Long> getUnsolvedIssueIds() {
        return transactions.stream()
                .flatMap(transaction -> transaction.getIssues().stream()
                        .filter(issue -> issue.getIssueId() != null && !issue.getIssueSolved())
                        .map(Issue::getIssueId))
                .collect(Collectors.toSet());
    }

    public List<String> getAllSolvedIssueMessages() {
        return transactions.stream()
                .flatMap(transaction -> transaction.getIssues().stream()
                        .filter(issue -> issue.getIssueId() != null && issue.getIssueSolved())
                        .map(Issue::getIssueMessage)).toList();
    }

    public List<Transaction> getTop3TransactionsByAmount() {
        return transactions.stream()
                .sorted((t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()))
                .limit(3).toList();
    }

    public Optional<String> getTopSender() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getSenderFullName, Collectors.summingDouble(Transaction::getAmount)))
                .entrySet().stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);
    }
}
