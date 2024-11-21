package gov.elections.userservice.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import gov.elections.userservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class FirestoreUserService {

    private final Firestore firestore;

    public void saveUser(User user) throws ExecutionException, InterruptedException {
        firestore.collection("users").document(user.getId()).set(user).get();
    }

    public User getUserById(String id) throws ExecutionException, InterruptedException {
        DocumentSnapshot snapshot = firestore.collection("users").document(id).get().get();
        return snapshot.exists() ? snapshot.toObject(User.class) : null;
    }
    public Boolean findVotedStatusById(String userId) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = firestore.collection("users").document(userId).get().get();
        if (document.exists() && document.contains("hasVoted")) {
            return document.getBoolean("hasVoted");
        }
        return null;
    }
}

