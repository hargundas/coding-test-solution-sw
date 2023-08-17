package com.smallworld.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.exception.FileLoadException;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${file.json.location}")
    private String jsonFileLocation;

    private List<Transaction> transactions = new ArrayList<>();

    @PostConstruct
    private void init() {
        try {
            log.info("loading file...");
            getAllTransactions();
            log.info("file loaded successfully");
        } catch (IOException e) {
            throw new FileLoadException("Error while loading file", e);
        }
    }


    @Override
    public List<Transaction> getAllTransaction() {
        return transactions;
    }

    @Override
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        if(transactions.isEmpty())
            return Map.of();

        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }

    @Override
    public double getTotalTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        return transactions.stream()
                .filter(transaction -> senderFullName.equals(transaction.getSenderFullName()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public double getMaxTransactionAmount() {
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .max()
                .orElse(0.0);
    }

    @Override
    public List<Transaction> getTop3TransactionsByAmount() {
        return transactions.stream()
                .sorted((t1, t2) -> Double.compare(t2.getAmount(), t1.getAmount()))
                .limit(3).toList();
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    private void getAllTransactions() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(jsonFileLocation);
        transactions = mapper.readValue(file, new TypeReference<>() {});
    }





}
