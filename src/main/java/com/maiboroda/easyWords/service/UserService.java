package com.maiboroda.easyWords.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiboroda.easyWords.domain.User;
import com.maiboroda.easyWords.dto.UserDTO;
import com.maiboroda.easyWords.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    private ConversionService conversionService;

    @Autowired
    public UserService(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> conversionService.convert(user, UserDTO.class)).collect(Collectors.toList());
    }

    public UserDTO getById(int id) {
        return conversionService.convert(userRepository.findById(id), UserDTO.class);
    }

    @Transactional
    public UserDTO add(UserDTO userDTO) {
        User user = conversionService.convert(userDTO, User.class);
        User savedUser = userRepository.save(user);

        return conversionService.convert(savedUser, UserDTO.class);
    }

    @Transactional
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO update(int id, UserDTO userDTO) {
        userRepository.updateUser(id, userDTO.getPassword());

        return conversionService.convert(userRepository.findById(id).get(), UserDTO.class);
    }
}
