package org.salpa.market.dto.user;

import lombok.Value;
import org.salpa.market.entity.user.Role;

@Value
public class UserReadDto {
    Long id;
    String login;
    String username;
    String password;
    Double amount;
    Role role;
}
