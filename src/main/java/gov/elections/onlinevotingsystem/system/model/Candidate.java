package gov.elections.onlinevotingsystem.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Candidate {
    @Id
    private Integer candidateNo;
    private String candidateNameAndParty;

    @Column(nullable = false)
    private int voteCount = 0;
    private double percentage;

}
