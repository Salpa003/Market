package org.salpa.market.service;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.entity.User;
import org.salpa.market.mapper.UserReadDtoMapper;
import org.salpa.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReadDtoMapper userReadDtoMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserReadDto> getAllUsers() {
        List<User> users = userRepository.findAll();
       return users.stream().map(userReadDtoMapper::map).collect(Collectors.toList());
    }

    public void saveUser() {

    }
}
