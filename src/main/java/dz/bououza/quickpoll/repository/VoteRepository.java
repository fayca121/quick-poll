package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Long> {
    @Query(value = "select v.* from proposal p, vote v where p.POLL_ID = ?1 and v.PROPOSAL_ID=p.PROPOSAL_ID",
            nativeQuery = true)
    Iterable<Vote> findByPoll(Long pollId);
}
