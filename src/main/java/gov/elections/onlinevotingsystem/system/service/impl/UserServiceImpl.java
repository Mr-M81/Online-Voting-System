package gov.elections.onlinevotingsystem.system.service.impl;

import gov.elections.onlinevotingsystem.system.common.Status;
import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.dto.EmailRequest;
import gov.elections.onlinevotingsystem.system.dto.UserRequest;
import gov.elections.onlinevotingsystem.system.dto.UserResponse;
import gov.elections.onlinevotingsystem.system.exception.BusinessException;
import gov.elections.onlinevotingsystem.system.mapper.UserMapper;
import gov.elections.onlinevotingsystem.system.model.User;
import gov.elections.onlinevotingsystem.system.repository.FirestoreUserRepository;
import gov.elections.onlinevotingsystem.system.repository.UserRepository;
import gov.elections.onlinevotingsystem.system.service.UserService;
import gov.elections.onlinevotingsystem.system.security.PasswordEncryption;
import gov.elections.onlinevotingsystem.system.service.VoterEmailService;
import gov.elections.onlinevotingsystem.system.util.ValidationUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;
    private final FirestoreUserRepository firestoreUserRepository;
    private final PasswordEncryption passwordEncryption;
    private final VoterEmailService voterEmailService;

    @Override
    public ResponseEntity<ApiResponse> save(@NonNull final UserRequest userRequest) {
        ApiResponse<Object> apiResponse;
        try {
            User user = UserMapper.MAPPER.toUser(userRequest);
            log.info("UserServiceImpl.save: Validating the user Email and ID");
            User existingUser = firestoreUserRepository.getUserByEmail(user.getEmail());
            if (existingUser != null) {
                log.warn("UserServiceImpl.save: User with email {} already exists", user.getEmail());
                apiResponse = ApiResponse.builder()
                        .status(Status.FAILED)
                        .message("User with this email already exists!")
                        .build();
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }
            if (validationUtil.isEmailValid(user.getEmail())) {

                log.info("UserServiceImpl.save: Saving the user {}", userRequest.getName());
                log.info("UserServiceImpl.password encryption: Encrypting the password");
                String encryptedPassword = passwordEncryption.hashPassword(user.getPassword());

                user.setPassword(encryptedPassword);
                userRepository.save(user);
                firestoreUserRepository.saveUser(user);

                UserResponse userResponse = UserMapper.MAPPER.toUserResponse(user);
                apiResponse = ApiResponse.builder()
                        .status(Status.SUCCESSFUL)
                        .data(userResponse)
                        .message("User saved successfully!")
                        .build();

            } else {
                log.warn("UserServiceImpl.save: Email or ID is invalid");
                apiResponse = ApiResponse.builder()
                        .status(Status.FAILED)
                        .message("Email or identification number is invalid")
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
            }

        } catch (Exception ex) {
            log.error("UserServiceImpl.save: An error occurred while saving the user", ex);
            throw new BusinessException("An error occurred while trying to save the user");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> update(@NonNull final UserRequest userRequest){
        log.info("UserServiceImpl.update: Update the user with ID {}",userRequest.getId());
        ApiResponse<UserResponse> apiResponse;

        try {
            User existingUser = userRepository.findById(String.valueOf(userRequest.getId()))
                    .orElseThrow();

            existingUser.setName(userRequest.getName());
            existingUser.setEmail(userRequest.getEmail());
            existingUser.setPassword(userRequest.getPassword());
            User updatedUser = userRepository.save(existingUser);
            UserResponse userResponse = UserMapper.MAPPER.toUserResponse(updatedUser);

            apiResponse = ApiResponse.<UserResponse>builder()
                    .data(userResponse)
                    .message("User name has been updated to "+ userRequest.getName())
                    .build();
            log.info("UserServiceImpl.update: User successfully updated their details");


        } catch (Exception exception){
            log.error("UserServiceImpl.update: There has been error saving user details");
            apiResponse = ApiResponse.<UserResponse>builder()
                    .data(null)
                    .message("Could not update")
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    public ResponseEntity<ApiResponse> read(@NonNull final String email,
                                            @NonNull final String enteredPassword) {
        log.info("UserService.read: Retrieving the user by their email: {}", email);

        try {
            UserResponse userResponse = firestoreUserRepository.readUserDetailsByEmail(email);
            log.debug("Retrieved user response: {}", userResponse);  // Add this line

            boolean isPasswordValid = passwordEncryption.verifyPassword(
                    enteredPassword,
                    userResponse.getPassword()
            );

            if (isPasswordValid) {
                userResponse.setPassword(null);
                ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                        .status(Status.SUCCESSFUL)
                        .data(userResponse)
                        .message("User logged in successfully!")
                        .build();
                return ResponseEntity.ok(apiResponse);
            } else {
                ApiResponse<Object> apiResponse = ApiResponse.builder()
                        .status(Status.FAILED)
                        .message("Invalid password")
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
            }
        } catch (EntityNotFoundException ex) {
            log.error("User with email {} not found", email, ex);
            ApiResponse<Object> apiResponse = ApiResponse.builder()
                    .status(Status.FAILED)
                    .message("User not found: " + ex.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception ex) {
            log.error("Detailed error in service: ", ex);  // Log full stack trace
            throw new BusinessException("Error details: " + ex.getMessage());
        }
    }


    public ResponseEntity<ApiResponse> findVotedStatusByEmail(String email) {
        log.info("UserServiceImpl.checkIfUserVoted: Checking voted status for user  {}", email);

        ApiResponse<Object> apiResponse;
        try {
            Boolean hasVoted = firestoreUserRepository.findVotedStatusByEmail(email);
            if (hasVoted != null) {
                apiResponse = ApiResponse.builder()
                        .status(Status.SUCCESSFUL)
                        .data(hasVoted)
                        .message("Voted status retrieved successfully")
                        .build();
            } else {
                apiResponse = ApiResponse.builder()
                        .status(Status.FAILED)
                        .message("User not found")
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
            }
        } catch (Exception ex) {
            log.error("UserServiceImpl.checkIfUserVoted: Error occurred while checking voted status for user ID {}", email);
            throw new BusinessException("An error occurred while checking the user's voted status");
        }

        return ResponseEntity.ok(apiResponse);
    }

}
