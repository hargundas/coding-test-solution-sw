package com.smallworld.controller;

import com.smallworld.constant.EndpointConstants;
import com.smallworld.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(EndpointConstants.CLIENT)
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/unique-count")
    public ResponseEntity<Long> countUniqueClients() {
        long uniqueClientCount = clientService.countUniqueClients();
        return new ResponseEntity<>(uniqueClientCount, HttpStatus.OK);
    }

    @GetMapping("/open-compliance-issues")
    public ResponseEntity<Boolean> hasOpenComplianceIssues(@RequestParam String clientFullName) {
        boolean hasOpenIssues = clientService.hasOpenComplianceIssues(clientFullName);
        return new ResponseEntity<>(hasOpenIssues, HttpStatus.OK);
    }

    @GetMapping("/top-sender")
    public ResponseEntity<Optional<String>> getTopSender() {
        Optional<String> topSender = clientService.getTopSender();
        return new ResponseEntity<>(topSender, HttpStatus.OK);
    }
}

