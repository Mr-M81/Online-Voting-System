package gov.elections.onlinevotingsystem.system.mapper;

import gov.elections.onlinevotingsystem.system.dto.CandidateRequest;
import gov.elections.onlinevotingsystem.system.dto.CandidateResponse;
import gov.elections.onlinevotingsystem.system.model.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CandidateMapper {

    CandidateMapper MAPPER = Mappers.getMapper(CandidateMapper.class);

    Candidate toCandidates(CandidateRequest candidateRequest);
    CandidateResponse toCandidateResponse(Candidate candidate);
}
