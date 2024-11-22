package gov.elections.onlinevotingsystem.system.util;

import gov.elections.onlinevotingsystem.system.dto.UserCheckResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
@Slf4j
public class ValidationUtil {
    private static final String USER_CHECK_URL = "https://api.usercheck.com/email/";
    private static final String VERIFY_ID_URL = "https://verifyid.co.za/rest-api"; // TODO: need to use the correct endpoint.
    private final WebClient.Builder webClientBuilder;

    public boolean isEmailValid(@NonNull final String email) {
        UserCheckResponse response = webClientBuilder.build()
                .get()
                .uri(USER_CHECK_URL + email)
                .retrieve()
                .bodyToMono(UserCheckResponse.class)
                .block();
        return response != null && !response.isDisposable();
    }

    // TODO: Change UserCheckResponse to the data you will recieve from Verify id.
    public boolean isIdentificationNumberValid(@NonNull final String identificationNumber) {
//        UserCheckResponse response = webClientBuilder.build()
//                .get()
//                .uri(VERIFY_ID_URL) // TODO: use the correct endpoint uri.
//                .retrieve()
//                .bodyToMono(UserCheckResponse.class)
//                .block();
//        return response != null && !response.isDisposable(); // TODO: Might return a different response.
        return true;
    }

}