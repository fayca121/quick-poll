package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll,Long> {
}
