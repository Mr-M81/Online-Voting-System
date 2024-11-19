package gov.elections.userservice.mapper;

import gov.elections.userservice.dto.UserRequest;
import gov.elections.userservice.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import gov.elections.userservice.model.User;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);
}
