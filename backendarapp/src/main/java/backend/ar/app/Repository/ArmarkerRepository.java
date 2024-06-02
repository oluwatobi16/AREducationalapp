package backend.ar.app.Repository;
import backend.ar.app.model.Armarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmarkerRepository extends JpaRepository<Armarker,Integer> {
}
