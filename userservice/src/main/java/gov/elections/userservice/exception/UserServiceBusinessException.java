package gov.elections.userservice.exception;

public class UserServiceBusinessException extends RuntimeException {
    public UserServiceBusinessException(String message) {
        super(message);
    }
}
