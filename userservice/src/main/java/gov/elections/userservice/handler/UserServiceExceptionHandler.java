package gov.elections.userservice.handler;

import gov.elections.userservice.common.Status;
import gov.elections.userservice.dto.Response;
import gov.elections.userservice.exception.UserServiceBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(UserServiceBusinessException.class)
    public ResponseEntity<Response> handleUserServiceBusinessExceptions(UserServiceBusinessException exception) {
        Response response = Response.builder()
                .status(Status.FAILED)
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
