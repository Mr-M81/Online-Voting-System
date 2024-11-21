package gov.elections.candidateservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CandidatesRequest {
    private Integer candidateNo;
    private String candidateNameAndParty;
}
