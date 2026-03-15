package org.salpa.market.dto;

import lombok.Value;

@Value
public class UserReadDto {
    String username;
    byte[] avatar;
}
