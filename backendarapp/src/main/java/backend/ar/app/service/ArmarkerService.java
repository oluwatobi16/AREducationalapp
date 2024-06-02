package backend.ar.app.service;
import backend.ar.app.model.Armarker;
import backend.ar.app.model.Location;
import backend.ar.app.Repository.ArmarkerRepository;
import backend.ar.app.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
@Service
public class ArmarkerService {
    private final ArmarkerRepository armarkerRepository;
    private final LocationRepository locationRepository;
    @Autowired
    public ArmarkerService(ArmarkerRepository armarkerRepository,LocationRepository locationRepository){
        this.armarkerRepository=armarkerRepository;
        this.locationRepository=locationRepository;
    }
    public List<Armarker> getAllArmarkers() {
        return armarkerRepository.findAll();
    }
    public Optional<Armarker> getArmarkerById(Integer id) {
        return armarkerRepository.findById(id);
    }
    public Armarker addArmarker(Armarker armarker){
        // check if the location already exists or needs to be created/updated
        Location location = armarker.getLocation();
        if (location != null && location.getId() == null) {
            locationRepository.save(location);
        }
        return armarkerRepository.save(armarker);
    }
    public Armarker updateArmarker(Integer id, Armarker updatedArmarker) {
        Armarker existingArmarker = getArmarkerById(id)
                .orElseThrow(() -> new RuntimeException("Armarker not found"));
        existingArmarker.setDescription(updatedArmarker.getDescription());

        // Update the Location
        if (updatedArmarker.getLocation() != null) {
            // If the course already has a location, update it
            Location existingLocation = existingArmarker.getLocation();
            if (existingLocation != null) {
                // Update existing location fields
                existingLocation.setLatitude(updatedArmarker.getLocation().getLatitude());
                existingLocation.setLongitude(updatedArmarker.getLocation().getLongitude());
                locationRepository.save(existingLocation);
            } else {
                // If the course does not have an existing location, save the new one
                Location newLocation = locationRepository.save(updatedArmarker.getLocation());
                existingArmarker.setLocation(newLocation);
            }
        }

        // Save and return the updated course
        return armarkerRepository.save(existingArmarker);
    }
    public void deleteArmarker(Integer id) {
        armarkerRepository.deleteById(id);
    }
}
