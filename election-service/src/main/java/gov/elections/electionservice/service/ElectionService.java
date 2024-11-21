package gov.elections.electionservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
@AllArgsConstructor
@Slf4j
public class ElectionService {
    private final WebClient.Builder webClientBuilder;

    public ResponseEntity<String> castVote(String id) {

        // Retrieve the "voted" status from the User Service
        Boolean hasVoted = webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port(8081)
                        .path("/api/v1/users/check-voted")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        // Check if the user has already voted
        if (Boolean.TRUE.equals(hasVoted)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User has already voted!");
        }

        log.info("ElectionService.castVote: User is eligible to vote.");


        return ResponseEntity.ok("Vote cast successfully!");
    }
}
//        User user = userRepository.findById(userId) //TODO: Verifiy if the user already exists;
//                .orElseThrow(() -> new IllegalArgumentException("User does not exist, must register"));
//
//        if(user.isVoted()){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not allowed to vote more than once!");
//        }
//
//        Candidates candidate = candidateRepository.findById(candidateId).orElseThrow(() ->
//                new IllegalArgumentException("Candidate not found"));
//
//        candidate.setVoteCount(candidate.getVoteCount() + 1);
//        candidateRepository.save(candidate);
//        user.setVoted(true);
//        userRepository.save(user);
//        return ResponseEntity.ok("Vote cast successfully!");

