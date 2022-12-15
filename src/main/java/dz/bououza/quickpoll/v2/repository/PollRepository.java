package dz.bououza.quickpoll.v2.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("PollRepositoryV2")
public interface PollRepository extends CrudRepository<Poll,Long> {
}
