package tamna6.tfm2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tamna6.tfm2022.entity.TfmUser;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public interface TfmUserRepository extends JpaRepository<TfmUser, String> {

    @Query(value =
            "SELECT id FROM tfm_user WHERE id = :dtoId",
            nativeQuery = true)
    String findByUserId(@Param("dtoId") String dtoId);

    @Query(value =
            "SELECT pw FROM tfm_user WHERE id = :dtoId AND pw = :dtoPw",
            nativeQuery = true)
    String findByPw(@Param("dtoId") String dtoId, @Param("dtoPw") String dtoPw);
    //비밀번호 중복되는 계정 있을 경우 대비하여 조건절에 ID 부분 추가함
}
