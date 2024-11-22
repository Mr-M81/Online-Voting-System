package gov.elections.onlinevotingsystem.system.service;

import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.dto.UserRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponse> save(@NonNull final UserRequest userRequest);

    ResponseEntity<ApiResponse> update(@NonNull final UserRequest userRequest);

    ResponseEntity<ApiResponse> read(@NonNull final String email, String enteredPassword);
}
