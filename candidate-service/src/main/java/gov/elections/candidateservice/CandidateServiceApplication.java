package gov.elections.candidateservice;

import gov.elections.candidateservice.repository.CandidateRepository;
import gov.elections.candidateservice.model.Candidates;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CandidateServiceApplication {

    public static void main(String [] args) {
        SpringApplication.run(CandidateServiceApplication.class, args); }

    @Bean
    public CommandLineRunner Loaddata(CandidateRepository CandidateRepository){
        return args -> {
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
}
