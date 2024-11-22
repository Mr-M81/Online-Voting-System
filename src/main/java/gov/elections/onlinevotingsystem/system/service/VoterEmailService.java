package gov.elections.onlinevotingsystem.system.service;


import gov.elections.onlinevotingsystem.system.dto.EmailRequest;
import gov.elections.onlinevotingsystem.system.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class VoterEmailService {

    private final JavaMailSender mailSender;
    private final User userRequest;

    public ResponseEntity<String> sendEmailUponRegistration(@NotNull final EmailRequest emailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("masolakabelo@gmail.com");
            message.setTo(emailRequest.getRecipient());
            message.setSubject(emailRequest.getSubject());
            message.setText("Dear Voter,\n\n" +
                    "Congratulations and welcome to the Online Voting System!\n\n" +
                    "We are thrilled to have you as part of this transformative platform where every vote counts and helps shape the future.\n\n" +
                    "With our user-friendly system, casting your vote is now easier and more secure than ever before. We value your participation in this democratic process and are here to ensure your voting experience is smooth and hassle-free.\n\n" +
                    "Thank you for registering and taking this important step. Let your voice be heard!\n\n" +
                    "Warm regards,\n" +
                    "The Online Voting Team");
            mailSender.send(message);


            log.info("Mail sent successfully to {}", emailRequest.getRecipient());
        } catch (MailException ex) {
            log.error("Mail sending error: {}", ex.getMessage());
            throw new RuntimeException("Unable to send email. Please try again later.");
        }
        return ResponseEntity.ok("Email sent successfully");
    }


    public ResponseEntity<String> sendEmailAfterVote(@NotNull final EmailRequest emailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("masolakabelo@gmail.com");
            message.setTo(emailRequest.getRecipient());
            message.setSubject(emailRequest.getSubject());
            message.setText("Dear Voter,\n\n" +
                    "Thank you for casting your vote! Your participation is greatly appreciated.\n\n" +
                    "For any inquiries, please visit our FAQs section. You also have the opportunity to view live results on our platform.\n\n" +
                    "Warm regards,\n" +
                    "The Online Voting Team");
            mailSender.send(message);


            log.info("Mail sent successfully to {}", emailRequest.getRecipient());
        } catch (MailException ex) {
            log.error("Mail sending error: {}", ex.getMessage());
            throw new RuntimeException("Unable to send email. Please try again later.");
        }
        return ResponseEntity.ok("Email sent successfully");
    }
}



