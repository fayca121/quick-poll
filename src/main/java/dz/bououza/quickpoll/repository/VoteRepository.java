package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote,Long> {
    @Query(value = "select v.* from Option o,Vote v where o.POLL_ID = ?1 and v.OPTION_ID=o.OPTION_ID",
            nativeQuery = true)
    Iterable<Vote> findByPoll(Long pollId);
}
