package com.hits.sport.mapper;

import com.hits.sport.dto.RegisterDto;
import com.hits.sport.dto.ShortUserDto;
import com.hits.sport.dto.UserDto;
import com.hits.sport.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "isTrainer", ignore = true)
    UserDto map(User user);

    ShortUserDto mapToShort(User user);
    @Mapping(target = "password", ignore = true)
    User map(RegisterDto dto);
}
