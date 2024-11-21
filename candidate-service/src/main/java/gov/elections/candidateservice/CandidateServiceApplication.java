package gov.elections.candidateservice;

import gov.elections.candidateservice.repository.CandidateRepository;
import gov.elections.candidateservice.model.Candidates;
import gov.elections.candidateservice.repository.FirebaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class CandidateServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CandidateServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner Loaddata(CandidateRepository CandidateRepository) {
        return args -> {
            log.info("Attempting to save the candidates for H2");

            Candidates candidate1 = new Candidates();
            candidate1.setCandidateNo(1);
            candidate1.setCandidateNameAndParty("Cyril Ramaphosa - ANC");

            Candidates candidate2 = new Candidates();
            candidate2.setCandidateNo(2);
            candidate2.setCandidateNameAndParty("Julius Malema - Eff");

            CandidateRepository.save(candidate1);
            CandidateRepository.save(candidate2);

        };
    }

    public CommandLineRunner LoaddataFire(FirebaseRepository firebaseRepository) {
        return args -> {
            log.info("Attempting to save the candidates for firebase");
            Candidates candidate1 = new Candidates();
            candidate1.setCandidateNo(1);
            candidate1.setCandidateNameAndParty("Cyril Ramaphosa - ANC");

            Candidates candidate2 = new Candidates();
            candidate2.setCandidateNo(2);
            candidate2.setCandidateNameAndParty("Julius Malema - Eff");

            Candidates candidate3 = new Candidates();
            candidate2.setCandidateNo(3);
            candidate2.setCandidateNameAndParty("John Steenhuisen - DA");

            Candidates candidate4 = new Candidates();
            candidate2.setCandidateNo(3);
            candidate2.setCandidateNameAndParty("Michael Beaumont - ActionSA");

            firebaseRepository.SaveCandidate(candidate1);
            firebaseRepository.SaveCandidate(candidate2);
            firebaseRepository.SaveCandidate(candidate3);
            firebaseRepository.SaveCandidate(candidate4);

        };
    }
}
