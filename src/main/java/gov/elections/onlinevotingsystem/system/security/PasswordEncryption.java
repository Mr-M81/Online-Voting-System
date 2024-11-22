package gov.elections.onlinevotingsystem.system.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@AllArgsConstructor
public class PasswordEncryption {

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder();
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public boolean verifyPassword(String enteredPassword, String storedHash) throws NoSuchAlgorithmException {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(storedHash);
    }
}