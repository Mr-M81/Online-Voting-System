package gov.elections.onlinevotingsystem.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Data
@Entity
@Table(name = "Users")
@Service
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    private String email;

    @Column(nullable = false)
    private boolean hasVoted = false;

    public int votenumber;

}


