package org.salpa.market.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class UserCreateDto {
    String login;

    String password;

    String username;

    MultipartFile avatar;
}
