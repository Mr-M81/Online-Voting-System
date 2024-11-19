package gov.elections.candidateservice.repository;

import gov.elections.candidateservice.model.Candidates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidates, String> {
}
