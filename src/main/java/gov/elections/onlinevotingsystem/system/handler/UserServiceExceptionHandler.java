package gov.elections.onlinevotingsystem.system.handler;

import gov.elections.onlinevotingsystem.system.common.Status;
import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> handleUserServiceBusinessExceptions(BusinessException exception) {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(Status.FAILED)
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
