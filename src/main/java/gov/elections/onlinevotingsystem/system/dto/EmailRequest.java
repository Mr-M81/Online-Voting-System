package gov.elections.onlinevotingsystem.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmailRequest {
    private String recipient;
    private String subject;
    private String body;
}