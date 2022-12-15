package dz.bououza.quickpoll.v1.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll,Long> {
}
