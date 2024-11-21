package gov.elections.userservice.repository;

import gov.elections.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u.hasVoted FROM User u WHERE u.id = :id")
    Boolean findVotedStatusById(@Param("id") String IdentificationNumber);
}
