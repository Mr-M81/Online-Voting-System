package gov.elections.onlinevotingsystem.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CandidateRequest {
    private Integer candidateNo;
    private String candidateNameAndParty;
    private int voteCount;
    private double percentage;
}
