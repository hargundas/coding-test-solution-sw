package com.smallworld.service.impl;

import com.smallworld.service.ClientService;

import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    @Override
    public long countUniqueClients() {
        return 0;
    }

    @Override
    public boolean hasOpenComplianceIssues(String clientFullName) {
        return false;
    }

    @Override
    public Optional<String> getTopSender() {
        return Optional.empty();
    }
}
