package gov.elections.userservice.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserRequest {
    private Integer id;
    private String name;
    private String password;
    private String email;
}
