package gov.elections.electionservice.controller;

import gov.elections.electionservice.service.ElectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/vote")
@Slf4j
public class ElectionController {

    private  final ElectionService electionService;

    @PostMapping("/cast-vote")
    public ResponseEntity<String> castVote(@RequestBody String id) {
        log.info("ElectionController.castVote {}", id);
        return electionService.castVote(id);
    }
}
