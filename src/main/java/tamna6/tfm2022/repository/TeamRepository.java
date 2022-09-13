package tamna6.tfm2022.repository;

import org.springframework.data.repository.CrudRepository;
import tamna6.tfm2022.entity.Team;

import java.util.ArrayList;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    @Override
    List<Team> findAll();
}
