package gov.elections.onlinevotingsystem.system.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import gov.elections.onlinevotingsystem.system.model.Candidate;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@AllArgsConstructor
@Service
@Slf4j
public class FirestoreCandidateRepository {

    private final Firestore firestore;

    public void SaveCandidate(Candidate candidate) throws ExecutionException, InterruptedException {
        firestore.collection("candidates").document(String.valueOf(candidate.getCandidateNo())).set(candidate).get();
    }
    public Candidate findCandidateById(Integer candidateNo) throws ExecutionException, InterruptedException {
        try {
            // Fetch the candidate document from Firestore
            DocumentSnapshot documentSnapshot = firestore
                    .collection("candidates")
                    .document(String.valueOf(candidateNo))
                    .get()
                    .get();

            // Check if the document exists
            if (!documentSnapshot.exists()) {
                throw new EntityNotFoundException("Candidate with ID " + candidateNo + " not found.");
            }
            return documentSnapshot.toObject(Candidate.class);
        } catch (ExecutionException | InterruptedException ex) {
            log.error("Error fetching candidate with ID {}", candidateNo, ex);
            throw ex; // Rethrow the exception to let the calling method handle it
        }
    }
}
