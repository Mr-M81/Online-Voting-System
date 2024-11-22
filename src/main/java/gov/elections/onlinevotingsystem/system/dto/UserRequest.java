package gov.elections.onlinevotingsystem.system.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.ThreadLocalRandom;

@Builder
@Data
@AllArgsConstructor
public class UserRequest {
    private String id;
    private String name;
    private String password;
    private String email;
    private int votenumber;

    public UserRequest() {
        this.votenumber = generatevoteNumber();
    }

    private int generatevoteNumber() {
        return ThreadLocalRandom.current().nextInt(10, 101);
    }
}
