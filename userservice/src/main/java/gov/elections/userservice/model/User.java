package gov.elections.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "User_TBL")
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;
}


