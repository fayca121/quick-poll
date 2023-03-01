package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PollRepository extends JpaRepository<Poll,Long> {
}
