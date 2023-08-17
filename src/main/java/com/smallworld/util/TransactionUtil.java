package com.smallworld.util;

import com.smallworld.model.Transaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionUtil {
    public static boolean hasOpenIssue(Transaction transaction, String clientFullName) {
        return (clientFullName.equals(transaction.getSenderFullName()) || clientFullName.equals(transaction.getBeneficiaryFullName()))
                && transaction.getIssues().stream()
                .anyMatch(issue -> issue.getIssueId() != null && !issue.getIssueSolved());
    }
}
