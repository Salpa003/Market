package org.salpa.market.dto;

import lombok.Value;

@Value
public class UserReadDto {
    Long id;
    String login;
    String username;
    String password;
    Double amount;
}
