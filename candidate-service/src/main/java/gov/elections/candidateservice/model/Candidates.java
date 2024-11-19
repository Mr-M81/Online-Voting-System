package gov.elections.candidateservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TBL_CANDIDATES")
public class Candidates {
    @Id
    private Integer candidateNo;
    private String candidateNameAndParty;

    @Column(nullable = false)
    private int voteCount = 0;
}
