package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PollRepository extends PagingAndSortingRepository<Poll, Long>,
        CrudRepository<Poll, Long> {
}
