package gov.elections.userservice.service;

import gov.elections.userservice.dto.Response;
import gov.elections.userservice.dto.UserRequest;
import gov.elections.userservice.dto.UserResponse;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Response> save(@NonNull final UserRequest userRequest);

    ResponseEntity<Response> update(@NonNull final UserRequest userRequest);
}
