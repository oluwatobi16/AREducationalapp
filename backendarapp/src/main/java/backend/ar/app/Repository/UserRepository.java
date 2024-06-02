package backend.ar.app.Repository;

import backend.ar.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    //custom query if needed

}
