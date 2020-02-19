package com.maiboroda.easyWords.converter;

import org.springframework.core.convert.converter.Converter;

import com.maiboroda.easyWords.domain.User;
import com.maiboroda.easyWords.dto.UserDTO;
import com.maiboroda.easyWords.service.DateService;

public class UserToUserDtoConverter implements Converter<User, UserDTO> {
    private DateService dateService;

    public UserToUserDtoConverter(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public UserDTO convert(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setUpdated(dateService.formatDate(user.getUpdated()));


        return userDTO;
    }
}
