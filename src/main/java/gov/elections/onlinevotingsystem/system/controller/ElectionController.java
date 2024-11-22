package gov.elections.onlinevotingsystem.system.controller;

import gov.elections.onlinevotingsystem.system.service.impl.ElectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/vote")
@Slf4j
public class ElectionController {

    private final ElectionService electionService;
    @PostMapping("/vote")
    public ResponseEntity<?> voteForCandidate(@RequestParam String email, @RequestParam Integer candidateNo) {
        return electionService.voteForCandidate(email, candidateNo);
    }
}
