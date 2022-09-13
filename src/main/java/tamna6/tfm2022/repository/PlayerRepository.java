package tamna6.tfm2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tamna6.tfm2022.entity.Player;
import tamna6.tfm2022.entity.Team;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query(value =
            "SELECT * FROM player WHERE tno = :tnoId",
            nativeQuery = true)
    List<Player> findByPlayer(@Param("tnoId") Long tnoId);
}
