package com.smallworld.service;

import com.smallworld.model.Transaction;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Transaction> getAllTransaction();
    Map<String, List<Transaction>> getTransactionsByBeneficiaryName();
    double getTotalTransactionAmount();
    double getTotalTransactionAmountSentBy(String senderFullName);
    double getMaxTransactionAmount();
    List<Transaction> getTop3TransactionsByAmount();
}
