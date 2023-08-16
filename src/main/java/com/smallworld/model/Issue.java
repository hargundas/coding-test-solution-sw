package com.smallworld.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    private Long issueId;
    private Boolean issueSolved;
    private String issueMessage;
}
