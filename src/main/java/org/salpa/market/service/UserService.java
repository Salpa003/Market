package org.salpa.market.service;

import lombok.RequiredArgsConstructor;
import org.salpa.market.dto.UserCreateDto;
import org.salpa.market.dto.UserReadDto;
import org.salpa.market.dto.UserUpdateDto;
import org.salpa.market.entity.user.Role;
import org.salpa.market.entity.user.User;
import org.salpa.market.exception.UserLoginException;
import org.salpa.market.mapper.UserReadDtoMapper;
import org.salpa.market.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserReadDtoMapper userReadDtoMapper;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final List<Character> symbols = Arrays.asList('.', '/', '\\');


    @Transactional(readOnly = true)
    public List<UserReadDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userReadDtoMapper::map).collect(Collectors.toList());
    }

    @Transactional
    public byte[] getUserAvatar(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty())
            return null;
        User user = maybeUser.get();
        Optional<byte[]> image = imageService.getImage(user.getAvatar());
        if (image.isEmpty())
            return null;
        return image.get();
    }

    @Transactional
    public Long saveUser(UserCreateDto createDto) {
        User user = User.builder()
                .login(createDto.getLogin())
                .password(createDto.getPassword())
                .username(createDto.getUsername())
                .role(Role.USER)
                .amount(0.0)
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

    @Transactional
    public void updateUser(Long id,UserUpdateDto updateDto) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty())
            return;
        User user = maybeUser.get();
        user.setUsername(updateDto.getUsername());
        user.setPassword(updateDto.getPassword());
        if (!updateDto.getAvatar().isEmpty()) {
            String fileName = user.getLogin() + getImageFormat(updateDto.getAvatar());
            try {
                imageService.updateImage(user.getAvatar(), fileName, updateDto.getAvatar().getInputStream());
                user.setAvatar(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserReadDto getUserById(Long id) {
        Optional<User> maybeUser = userRepository.findById(id);
        if (maybeUser.isEmpty())
            return userReadDtoMapper.map(User.builder().build());
        return userReadDtoMapper.map(maybeUser.get());
    }
    @Transactional(readOnly = true)
    public Long loginUser(String login, String password) throws UserLoginException {
        User user = userRepository.findUserByLogin(login);
        if (user == null)
            throw new UserLoginException("Такой логин не существует!");
        if (!user.getPassword().equals(password))
            throw new UserLoginException("Неверный пароль!");
        return user.getId();
    }

    public void validateLogin(String login) throws UserLoginException {
        User user = userRepository.findUserByLogin(login);
        if (user != null)
            throw new UserLoginException("Такой логин уже существует");
        else if (containsSymbols(login)) {
            AtomicReference<String> s = new AtomicReference<>("");
            symbols.forEach(c -> s.updateAndGet(v -> v + c + " "));
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

    private boolean containsSymbols(String login) {
        for (char c : login.toCharArray()) {
            if (symbols.contains(c))
                return true;
        }
        return false;
    }
}
