package gov.elections.candidateservice.repository;

import com.google.cloud.firestore.Firestore;
import gov.elections.candidateservice.model.Candidates;
import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutionException;

@AllArgsConstructor

public class FirebaseRepository {

    private final Firestore firestore;

    public void SaveCandidate(Candidates candidates) throws ExecutionException, InterruptedException {
        firestore.collection("candidates").document(String.valueOf(candidates.getCandidateNo())).set(candidates).get();
    }
}
