package gov.elections.onlinevotingsystem.system.controller;

import gov.elections.onlinevotingsystem.system.dto.EmailRequest;
import gov.elections.onlinevotingsystem.system.service.VoterEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final VoterEmailService emailService;

    @PostMapping("email/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        return emailService.sendEmailUponRegistration(emailRequest);
    }

}