package gov.elections.userservice.controller;

import gov.elections.userservice.service.impl.UserServiceImpl;
import gov.elections.userservice.dto.Response;
import gov.elections.userservice.dto.UserRequest;
import gov.elections.userservice.dto.UserResponse;
import lombok.AllArgsConstructor;
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
    @GetMapping("/read")
    public ResponseEntity<Response> read(@RequestParam String IdentificationNumber) {
        return userServiceImpl.read(IdentificationNumber);
    }
    @GetMapping("/check-voted")
    public ResponseEntity<Response> findVotedStatusByid(@RequestParam String id) {
        return userServiceImpl.findVotedStatusById(id);
    }
}
