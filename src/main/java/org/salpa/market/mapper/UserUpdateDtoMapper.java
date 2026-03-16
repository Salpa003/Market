package org.salpa.market.mapper;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserUpdateDto;
import org.salpa.market.entity.User;
import org.salpa.market.service.ImageService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUpdateDtoMapper implements Mapper<User, UserUpdateDto> {

    private final ImageService imageService;
    @Override
    public UserUpdateDto map(User user) {
//        return new UserUpdateDto(
//                user.getUsername(),
//                user.getPassword(),
//        );
        return null;
    }
}
