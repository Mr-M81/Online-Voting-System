package gov.elections.onlinevotingsystem.system.controller;

import gov.elections.onlinevotingsystem.system.common.Status;
import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.dto.LoginRequest;
import gov.elections.onlinevotingsystem.system.dto.UserRequest;
import gov.elections.onlinevotingsystem.system.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
@Slf4j
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserRequest userRequest) {
        return userServiceImpl.save(userRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody UserRequest userRequest) {
        return userServiceImpl.update(userRequest);
    }

    @PostMapping("/read")
    public ResponseEntity<ApiResponse> read(@RequestBody LoginRequest loginRequest) {
        try {
            log.info("Received login request for email: {}", loginRequest.getEmail());
            return userServiceImpl.read(loginRequest.getEmail(), loginRequest.getPassword());
        } catch (Exception e) {
            // Log the full stack trace
            log.error("Detailed error in controller: ", e);
            ApiResponse<Object> apiResponse = ApiResponse.builder()
                    .status(Status.FAILED)
                    .message("Error: " + e.getMessage())  // Include the actual error message
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }

    @GetMapping("/check-voted")
    public ResponseEntity<ApiResponse> findVotedStatusByEmail(@RequestParam String email) {
        return userServiceImpl.findVotedStatusByEmail(email);
    }
}
