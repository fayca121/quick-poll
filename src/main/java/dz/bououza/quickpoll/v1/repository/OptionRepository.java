package dz.bououza.quickpoll.v1.repository;

import dz.bououza.quickpoll.domain.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option,Long> {
}
