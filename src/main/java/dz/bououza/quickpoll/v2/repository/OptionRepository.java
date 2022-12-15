package dz.bououza.quickpoll.v2.repository;

import dz.bououza.quickpoll.domain.Option;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("OptionRepositoryV2")
public interface OptionRepository extends CrudRepository<Option,Long> {
}
