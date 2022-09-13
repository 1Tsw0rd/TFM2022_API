package tamna6.tfm2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tamna6.tfm2022.dto.TfmAuthDto;
import tamna6.tfm2022.entity.TfmAuth;

import java.time.LocalDateTime;
import java.util.List;

public interface TfmAuthRepository extends JpaRepository<TfmAuth, String> {

    @Modifying  //update, delete, insert 이런거 쓸 때 붙여야됨
    @Query(value =
            "UPDATE tfm_auth SET lastrequest = :nowTime WHERE id = :dtoId",
            nativeQuery = true)
    void updateLastrequest(@Param("nowTime") LocalDateTime nowTime, @Param("dtoId") String dtoId); //void 붙여야됨

    @Query(value =
            "SELECT * FROM tfm_auth " +
                    "WHERE token IS NOT NULL " +
                    "AND lastrequest < DATE_ADD(NOW(), INTERVAL -10 MINUTE);",
            nativeQuery = true)
    List<TfmAuth> findAbnormalAuth(); //현재 시간보다 10분 뒤에 있는 lastrequest 기록 검색
}