package org.salpa.market.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class UserUpdateDto {
    String username;
    String password;
    MultipartFile avatar;
}
