package com.smallworld.constant;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointConstants {
    public static final String TRANSACTION = "/transactions";
    public static final String ISSUE = "/issues";
    public static final String CLIENT = "/clients";
}
