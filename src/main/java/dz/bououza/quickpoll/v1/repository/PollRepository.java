package dz.bououza.quickpoll.v1.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("PollRepositoryV1")
public interface PollRepository extends CrudRepository<Poll,Long> {
}
