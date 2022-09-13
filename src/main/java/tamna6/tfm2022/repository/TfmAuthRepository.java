package tamna6.tfm2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import tamna6.tfm2022.entity.TfmAuth;

import java.time.LocalDateTime;

public interface TfmAuthRepository extends JpaRepository<TfmAuth, String> {

    @Modifying  //update, delete, insert 이런거 쓸 때 붙여야됨
    @Query(value =
            "UPDATE tfm_auth SET lastrequest = :nowTime WHERE id = :dtoId",
            nativeQuery = true)
    void updateLastrequest(@Param("nowTime") LocalDateTime nowTime, @Param("dtoId") String dtoId); //void 붙여야됨
}