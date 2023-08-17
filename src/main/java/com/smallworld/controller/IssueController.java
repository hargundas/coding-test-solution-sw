package com.smallworld.controller;

import com.smallworld.constant.EndpointConstants;
import com.smallworld.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(EndpointConstants.ISSUE)
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/unsolved-ids")
    public ResponseEntity<Set<Long>> getUnsolvedIssueIds() {
        Set<Long> unsolvedIssueIds = issueService.getUnsolvedIssueIds();
        return new ResponseEntity<>(unsolvedIssueIds, HttpStatus.OK);
    }

    @GetMapping("/solved-messages")
    public ResponseEntity<List<String>> getAllSolvedIssueMessages() {
        List<String> solvedIssueMessages = issueService.getAllSolvedIssueMessages();
        return new ResponseEntity<>(solvedIssueMessages, HttpStatus.OK);
    }
}
