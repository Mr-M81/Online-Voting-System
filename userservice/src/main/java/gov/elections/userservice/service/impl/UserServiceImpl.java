package gov.elections.userservice.service.impl;

import gov.elections.userservice.common.Status;
import gov.elections.userservice.exception.UserServiceBusinessException;
import gov.elections.userservice.repository.FirestoreUserService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import gov.elections.userservice.service.UserService;
import gov.elections.userservice.util.ValidationUtil;
import gov.elections.userservice.dto.Response;
import gov.elections.userservice.dto.UserRequest;
import gov.elections.userservice.dto.UserResponse;
import gov.elections.userservice.mapper.UserMapper;
import gov.elections.userservice.model.User;
import gov.elections.userservice.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final FirestoreUserService firestoreUserService;

    @Override
    public ResponseEntity<Response> save(@NonNull final UserRequest userRequest) { // TODO: add a checker to see if the user is registered or not.

        Response<Object> response;
        try {
            User user = UserMapper.MAPPER.toUser(userRequest);
            log.info("UserServiceImpl.save: Validating the user Email and ID");

            if (validationUtil.isEmailValid(user.getEmail())
                    && validationUtil.isIdentificationNumberValid(user.getId())) {

                log.info("UserServiceImpl.save: Saving the user {}", userRequest.getName());
                userRepository.save(user);
                firestoreUserService.saveUser(user);

                UserResponse userResponse = UserMapper.MAPPER.toUserResponse(user);
                response = Response.builder()
                        .status(Status.SUCCESSFUL)
                        .data(userResponse)
                        .message("User saved successfully!")
                        .build();
            } else {
                log.warn("No temporary emails allowed!");
                response = Response.builder()
                        .status(Status.FAILED)
                        .message("Email is invalid")
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception ex) {
            log.error("UserServiceImpl.save: An error occurred while saving the user");
            throw new UserServiceBusinessException("An error occurred while trying to save the user");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Response> update(@NonNull final UserRequest userRequest){
        log.info("UserServiceImpl.update: Update the user with ID {}",userRequest.getId());
        Response<UserResponse> response;

        try {
            User existingUser = userRepository.findById(String.valueOf(userRequest.getId()))
                    .orElseThrow();

            existingUser.setName(userRequest.getName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(userRequest.getPassword());
            User updatedUser = userRepository.save(existingUser);
            UserResponse userResponse = UserMapper.MAPPER.toUserResponse(updatedUser);

            response = Response.<UserResponse>builder()
                    .data(userResponse)
                    .message("User name has been updated to "+ userRequest.getName())
                    .build();
            log.info("UserServiceImpl.update: User successfully updated their details");


        } catch (Exception exception){
            log.error("UserServiceImpl.update: There has been error saving user details");
            response = Response.<UserResponse>builder()
                    .data(null)
                    .message("Could not update")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Response> read(@NonNull final String IdentificationNumber) {
        log.info("UserService.read: Retrieving the user by their ID: {}", IdentificationNumber);
        Response<Object> response;
        try {
            Optional<User> user = userRepository.findById(String.valueOf(IdentificationNumber));

            if(user.isPresent()) {
                UserResponse userResponse = UserMapper.MAPPER.toUserResponse(user.get());
                response = Response.builder()
                        .status(Status.SUCCESSFUL)
                        .data(userResponse)
                        .message("User fetched successfully!")
                        .build();
                return ResponseEntity.ok(response);

            } else {
                response = Response.builder()
                        .status(Status.FAILED)
                        .message("User not found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception ex) {
            log.error("UserServiceImpl.getByIdentificationNumber: An error occurred while fetching the user", ex);
            throw new UserServiceBusinessException("An error occurred while trying to fetch the user");
        }

    }
    public ResponseEntity<Response> findVotedStatusById(String id) {
        log.info("UserServiceImpl.checkIfUserVoted: Checking voted status for user ID {}", id);

        Response<Object> response;
        try {
            Boolean hasVoted = firestoreUserService.findVotedStatusById(id);
            if (hasVoted != null) {
                response = Response.builder()
                        .status(Status.SUCCESSFUL)
                        .data(hasVoted)
                        .message("Voted status retrieved successfully")
                        .build();
            } else {
                response = Response.builder()
                        .status(Status.FAILED)
                        .message("User not found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            log.error("UserServiceImpl.checkIfUserVoted: Error occurred while checking voted status for user ID {}", id);
            throw new UserServiceBusinessException("An error occurred while checking the user's voted status");
        }

        return ResponseEntity.ok(response);
    }
}
