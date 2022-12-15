package dz.bououza.quickpoll.v2.repository;

import dz.bououza.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository("PollRepositoryV2")
public interface PollRepository extends PagingAndSortingRepository<Poll, Long>,
        CrudRepository<Poll, Long> {
}
