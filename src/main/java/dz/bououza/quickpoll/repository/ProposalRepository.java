package dz.bououza.quickpoll.repository;

import dz.bououza.quickpoll.domain.Proposal;
import org.springframework.data.repository.CrudRepository;

public interface ProposalRepository extends CrudRepository<Proposal,Long> {
}
