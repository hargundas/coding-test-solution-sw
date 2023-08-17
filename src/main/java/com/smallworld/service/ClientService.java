package com.smallworld.service;

import java.util.Optional;

public interface ClientService {
    long countUniqueClients();
    boolean hasOpenComplianceIssues(String clientFullName);
    Optional<String> getTopSender();
}
