package com.smallworld.service;

import java.util.List;
import java.util.Set;

public interface IssueService {
    Set<Integer> getUnsolvedIssueIds();
    List<String> getAllSolvedIssueMessages();
}
