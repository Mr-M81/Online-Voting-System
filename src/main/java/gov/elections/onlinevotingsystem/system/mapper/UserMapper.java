package gov.elections.onlinevotingsystem.system.mapper;

import gov.elections.onlinevotingsystem.system.dto.UserRequest;
import gov.elections.onlinevotingsystem.system.dto.UserResponse;
import gov.elections.onlinevotingsystem.system.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
