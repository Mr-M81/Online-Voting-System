package gov.elections.onlinevotingsystem.system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateResponse {
    private Integer candidateNo;
    private String candidateNameAndParty;
    private int voteCount;
    private double percentage;

}
