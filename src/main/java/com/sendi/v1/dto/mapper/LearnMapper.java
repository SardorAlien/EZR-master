package com.sendi.v1.dto.mapper;

import com.sendi.v1.dto.UserDTO;
import com.sendi.v1.security.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LearnMapper {
    LearnMapper INSTANCE = Mappers.getMapper(LearnMapper.class);

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
