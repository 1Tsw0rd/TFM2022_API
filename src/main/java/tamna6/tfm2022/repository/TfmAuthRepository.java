package tamna6.tfm2022.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import tamna6.tfm2022.entity.TfmAuth;

public interface TfmAuthRepository extends JpaRepository<TfmAuth, String> {
}
