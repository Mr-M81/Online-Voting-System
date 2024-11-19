package gov.elections.userservice.controller;

import gov.elections.userservice.service.impl.UserServiceImpl;
import gov.elections.userservice.dto.Response;
import gov.elections.userservice.dto.UserRequest;
import gov.elections.userservice.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserRequest userRequest) {
        return userServiceImpl.save(userRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<Response> update(@RequestBody UserRequest userRequest) {
        return userServiceImpl.update(userRequest);
    }

}
