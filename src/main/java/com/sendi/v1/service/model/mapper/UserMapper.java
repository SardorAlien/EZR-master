package com.sendi.v1.service.model.mapper;

import com.sendi.v1.service.model.UserDTO;
import com.sendi.v1.security.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toDTOs(List<User> users);

    List<User> toEntities(List<UserDTO> userDTOs);
}
