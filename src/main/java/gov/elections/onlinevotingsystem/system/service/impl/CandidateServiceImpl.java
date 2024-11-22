package gov.elections.onlinevotingsystem.system.service.impl;

import gov.elections.onlinevotingsystem.system.common.Status;
import gov.elections.onlinevotingsystem.system.dto.CandidateRequest;
import gov.elections.onlinevotingsystem.system.dto.ApiResponse;
import gov.elections.onlinevotingsystem.system.dto.CandidateResponse;
import gov.elections.onlinevotingsystem.system.exception.BusinessException;
import gov.elections.onlinevotingsystem.system.mapper.CandidateMapper;
import gov.elections.onlinevotingsystem.system.model.Candidate;
import gov.elections.onlinevotingsystem.system.repository.FirestoreCandidateRepository;
import gov.elections.onlinevotingsystem.system.service.CandidateService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor

public class CandidateServiceImpl implements CandidateService {
    private final FirestoreCandidateRepository firestoreCandidateRepository;

    @Override
    public ResponseEntity<ApiResponse> saveCandidate(@NonNull final CandidateRequest candidateRequest) {

        ApiResponse<Object> apiResponse;

        try {
            Candidate candidate = CandidateMapper.MAPPER.toCandidates(candidateRequest);
            log.info("CandidateServiceImpl.saveCandidate: Saving {} to firebase", candidateRequest.getCandidateNameAndParty());
            firestoreCandidateRepository.SaveCandidate(candidate);

            CandidateResponse candidateResponse = CandidateMapper.MAPPER.toCandidateResponse(candidate);
            apiResponse = ApiResponse.builder()
                    .status(Status.SUCCESSFUL)
                    .data(candidateResponse)
                    .message("Candidate has been successfully saved")
                    .build();

        } catch (Exception ex) {
            log.error("UserServiceImpl.save: An error occurred while saving the user", ex);
            throw new BusinessException("An error occurred while trying to save the user");
        }

        // Place the return statement here
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }
}


