package com.smallworld.controller;

import com.smallworld.constant.EndpointConstants;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(EndpointConstants.TRANSACTION)
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransaction();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/beneficiary")
    public ResponseEntity<Map<String, List<Transaction>>> getTransactionsByBeneficiaryName() {
        Map<String, List<Transaction>> transactionsByBeneficiary = transactionService.getTransactionsByBeneficiaryName();
        return new ResponseEntity<>(transactionsByBeneficiary, HttpStatus.OK);
    }

    @GetMapping("/total-amount")
    public ResponseEntity<Double> getTotalTransactionAmount() {
        double totalAmount = transactionService.getTotalTransactionAmount();
        return new ResponseEntity<>(totalAmount, HttpStatus.OK);
    }

    @GetMapping("/total-amount-by-sender")
    public ResponseEntity<Double> getTotalTransactionAmountSentBy(@RequestParam String senderFullName) {
        double totalAmountSentBySender = transactionService.getTotalTransactionAmountSentBy(senderFullName);
        return new ResponseEntity<>(totalAmountSentBySender, HttpStatus.OK);
    }

    @GetMapping("/max-amount")
    public ResponseEntity<Double> getMaxTransactionAmount() {
        double maxAmount = transactionService.getMaxTransactionAmount();
        return new ResponseEntity<>(maxAmount, HttpStatus.OK);
    }

    @GetMapping("/top3-by-amount")
    public ResponseEntity<List<Transaction>> getTop3TransactionsByAmount() {
        List<Transaction> top3Transactions = transactionService.getTop3TransactionsByAmount();
        return new ResponseEntity<>(top3Transactions, HttpStatus.OK);
    }
}
