package com.smallworld.service;

import java.util.List;
import java.util.Set;

public interface IssueService {
    Set<Long> getUnsolvedIssueIds();
    List<String> getAllSolvedIssueMessages();
}
