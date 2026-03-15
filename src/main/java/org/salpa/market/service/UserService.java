package org.salpa.market.service;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserCreateDto;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.entity.User;
import org.salpa.market.exception.UserLoginException;
import org.salpa.market.mapper.UserReadDtoMapper;
import org.salpa.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReadDtoMapper userReadDtoMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final List<Character> symbols = Arrays.asList('.', '/','\\');


    @Transactional(readOnly = true)
    public List<UserReadDto> getAllUsers() {
        List<User> users = userRepository.findAll();
       return users.stream().map(userReadDtoMapper::map).collect(Collectors.toList());
    }

    @Transactional
    public Long saveUser(UserCreateDto createDto) {
        User user = User.builder()
                .login(createDto.getLogin())
                .password(createDto.getPassword())
                .username(createDto.getUsername())
                .build();

        MultipartFile avatar = createDto.getAvatar();
        String filename = user.getLogin() + getImageFormat(avatar);
        try {
            imageService.upload(filename, avatar.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setAvatar(filename);
        User saved = userRepository.save(user);
        return saved.getId();
    }


    public void validateLogin(String login) throws UserLoginException {
        User user = userRepository.findUserByLogin(login);
        if (user != null)
            throw new UserLoginException("Такой логин уже существует");
        else if (containsSymbols(login)) {
            AtomicReference<String> s = new AtomicReference<>("");
            symbols.forEach(c-> s.updateAndGet(v -> v + c + " "));
            throw new UserLoginException("Логин содержит запрещенные символы: " + s);
        }
    }


    private String getImageFormat(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null) {
            return null;
        }

        String fileName = file.getOriginalFilename();
        int lastDotIndex = fileName.lastIndexOf(".");

        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return null;
        }

        return fileName.substring(lastDotIndex).toLowerCase();
    }

    public boolean containsSymbols(String login) {
        for (char c: login.toCharArray()) {
            if (symbols.contains(c))
                return true;
        }
        return false;
    }
}
