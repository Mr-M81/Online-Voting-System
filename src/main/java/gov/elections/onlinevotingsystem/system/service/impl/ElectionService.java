package gov.elections.onlinevotingsystem.system.service.impl;

import gov.elections.onlinevotingsystem.system.common.Status;
import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.exception.BusinessException;
import gov.elections.onlinevotingsystem.system.model.Candidate;
import gov.elections.onlinevotingsystem.system.repository.FirestoreCandidateRepository;
import gov.elections.onlinevotingsystem.system.repository.FirestoreUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ElectionService {

    private final FirestoreUserRepository firestoreUserRepository;
    private final FirestoreCandidateRepository firestoreCandidateRepository;

    public ResponseEntity<ApiResponse> voteForCandidate(String email, Integer candidateNo) {
        log.info("Voting process initiated for user {} for candidate ID {}", email, candidateNo);

        ApiResponse<Object> apiResponse;

        try {
            Boolean hasVoted = firestoreUserRepository.findVotedStatusByEmail(email);

            if (Boolean.TRUE.equals(hasVoted)) {
                apiResponse = ApiResponse.builder()
                        .status(Status.FAILED)
                        .message("User has already voted")
                        .build();
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
            }

            Candidate candidate = firestoreCandidateRepository.findCandidateById(candidateNo);
            candidate.setVoteCount(candidate.getVoteCount() + 1);

            firestoreCandidateRepository.SaveCandidate(candidate);
            firestoreUserRepository.updateVotedStatus(email, true);

            apiResponse = ApiResponse.builder()
                    .status(Status.SUCCESSFUL)
                    .data(candidate)
                    .message("Vote successfully recorded")
                    .build();

            return ResponseEntity.ok(apiResponse);

        } catch (EntityNotFoundException ex) {
            log.error("Candidate or user not found during voting process. Candidate ID: {}, User {}", candidateNo, email, ex);
            apiResponse = ApiResponse.builder()
                    .status(Status.FAILED)
                    .message("Candidate or user not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
        } catch (Exception ex) {
            log.error("Error occurred during voting process for user {} and candidate ID {}", email, candidateNo, ex);
            throw new BusinessException("An error occurred while processing the vote.");
        }
    }
}
