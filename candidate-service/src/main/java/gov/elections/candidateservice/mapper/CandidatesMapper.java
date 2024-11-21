package gov.elections.candidateservice.mapper;

import gov.elections.candidateservice.dto.CandidatesRequest;
import gov.elections.candidateservice.model.Candidates;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CandidatesMapper {

    CandidatesMapper MAPPER = Mappers.getMapper(CandidatesMapper.class);

    Candidates toCandidates(CandidatesRequest candidatesRequest);
}
