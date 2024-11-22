package gov.elections.onlinevotingsystem.system.service;

import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.dto.CandidateRequest;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

public interface CandidateService {
    ResponseEntity<ApiResponse> saveCandidate(@NonNull final CandidateRequest candidateRequest);
}
