package gov.elections.onlinevotingsystem.system.controller;

import gov.elections.onlinevotingsystem.system.dto.CandidateRequest;
import gov.elections.onlinevotingsystem.system.service.impl.CandidateServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/candidates")

public class CandidatesController {

    private final CandidateServiceImpl candidateServiceImpl;

    @PostMapping("/saveCandidate")
    public ResponseEntity<?> saveCandidate(@RequestBody CandidateRequest candidateRequest) {
        return candidateServiceImpl.saveCandidate(candidateRequest);
    }
}
