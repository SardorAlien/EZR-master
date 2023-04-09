package com.sendi.v1.dto.mapper;

import com.sendi.v1.domain.Flashcard;
import com.sendi.v1.dto.FlashcardDTO;
import com.sendi.v1.dto.UserDTO;
import com.sendi.v1.security.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toUserDTOs(List<User> users);

    List<User> toUsers(List<UserDTO> userDTOs);
}
