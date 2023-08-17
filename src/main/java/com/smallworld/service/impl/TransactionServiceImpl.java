package com.smallworld.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            log.info("File loaded successfully");
        } catch (IOException e) {
            log.error("Error while loading file", e);
        }
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactions;
    }

    @Override
    public Map<String, List<Transaction>> getTransactionsByBeneficiaryName() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getBeneficiaryFullName));
    }



    private void getAllTransactions() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(jsonFileLocation);
        transactions = mapper.readValue(file, new TypeReference<>() {});
    }
}
