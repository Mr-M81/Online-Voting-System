package gov.elections.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCheckResponse {
    private Integer status;
    private String email;
    private String domain;
    private boolean mx;
    private boolean disposable;
    private boolean publicDomain;
    private boolean alias;
    private String didYouMean;
}

