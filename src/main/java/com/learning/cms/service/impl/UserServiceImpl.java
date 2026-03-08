package com.learning.cms.service.impl;

import com.learning.cms.dto.request.LoginRequestDTO;
import com.learning.cms.dto.request.RegisterRequestDTO;
import com.learning.cms.dto.response.AuthResponseDTO;
import com.learning.cms.dto.response.UserResponseDTO;
import com.learning.cms.entity.User;
import com.learning.cms.exception.InvalidRequestException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.mapper.UserMapper;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.UserService;
import com.learning.cms.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public UserResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InvalidRequestException("Email is already registered: " + request.getEmail());
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidRequestException("Invalid email or password");
        }

        return new AuthResponseDTO("SECURITY_DISABLED", userMapper.toResponseDTO(user));
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO uploadProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        String fileName = fileStorageUtil.storeFile(file);
        user.setProfilePicture(fileName);
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }
}
