package gov.elections.onlinevotingsystem.system.repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import gov.elections.onlinevotingsystem.system.dto.UserResponse;
import gov.elections.onlinevotingsystem.system.model.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class FirestoreUserRepository {

    private final Firestore firestore;

    public void saveUser(User user) throws ExecutionException, InterruptedException {
        firestore.collection("users")
                .document(user.getEmail()).set(user).get();
    }

    public User getUserByEmail(String email) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection("users")
                .document(email).get().get();
        return snapshot.exists() ? snapshot.toObject(User.class) : null;
    }


    public Boolean findVotedStatusByEmail(String email) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("users")
                .document(email);
        DocumentSnapshot documentSnapshot = documentReference.get().get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.getBoolean("hasVoted");
        } else {
            throw new EntityNotFoundException("User {} " + email + " not found.");
        }
    }
    public Boolean findLoginDetailsByEmail(String email) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("users")
                .document(email);
        DocumentSnapshot documentSnapshot = documentReference.get().get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.getBoolean("hasVoted");
        } else {
            throw new EntityNotFoundException("User {} " + email + " not found.");
        }
    }

    public Boolean readbyEmail(String email) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = firestore.collection("users").document(email);
        DocumentSnapshot documentSnapshot = documentReference.get().get();

        if (documentSnapshot.exists()) {
            return documentSnapshot.contains("password");
        } else {
            throw new EntityNotFoundException("User {} " + email + " not found.");
        }
    }

    public void updateVotedStatus(String email, Boolean hasVoted) {
        DocumentReference documentReference = firestore.collection("users")
                .document(email);
        documentReference.update("hasVoted", hasVoted);
    }

    public UserResponse readUserDetailsByEmail(String email)
            throws ExecutionException, InterruptedException {
        log.debug("Starting to read user details for email: {}", email);

        try {
            DocumentReference documentReference = firestore.collection("users")
                    .document(email);

            log.debug("Fetching document from Firestore...");
            DocumentSnapshot documentSnapshot = documentReference.get().get();

            if (documentSnapshot.exists()) {
                log.debug("Document exists, reading fields...");
                Long voteNumber = documentSnapshot.getLong("votenumber");
                String password = documentSnapshot.getString("password");
                String name = documentSnapshot.getString("name");
                String id = documentSnapshot.getString("id");

                log.debug("Read fields - name: {}, id: {}, voteNumber: {}",
                        name, id, voteNumber);

                return UserResponse.builder()
                        .email(email)
                        .password(password)
                        .name(name)
                        .id(id)
                        .votenumber(voteNumber != null ? voteNumber.intValue() : 0)
                        .build();
            } else {
                log.error("Document does not exist for email: {}", email);
                throw new EntityNotFoundException("User with email " + email + " not found.");
            }
        } catch (Exception e) {
            log.error("Detailed error in repository: ", e);  // Log full stack trace
            throw e;
        }
    }
}
