package backend.ar.app.Repository;

import backend.ar.app.model.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AuthenticationRepository extends JpaRepository<Authentication, Integer> {

}
