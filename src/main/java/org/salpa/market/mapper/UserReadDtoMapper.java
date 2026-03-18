package org.salpa.market.mapper;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.entity.user.User;
import org.salpa.market.service.ImageService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReadDtoMapper implements Mapper<User, UserReadDto> {

    private final ImageService imageService;
    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(
                user.getId(),
                user.getLogin(),
                user.getUsername(),
                user.getPassword(),
                user.getAmount(),
                user.getRole()
                );
    }
}


