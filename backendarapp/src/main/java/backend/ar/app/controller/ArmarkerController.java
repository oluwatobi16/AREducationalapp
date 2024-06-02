package backend.ar.app.controller;

import backend.ar.app.model.Armarker;
import backend.ar.app.service.ArmarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/armarkers")
public class ArmarkerController {

    private final ArmarkerService armarkerService;

    @Autowired
    public ArmarkerController(ArmarkerService armarkerService) {
        this.armarkerService = armarkerService;
    }

    @GetMapping
    public ResponseEntity<List<Armarker>> getAllArmarkers() {
        List<Armarker> armarkers = armarkerService.getAllArmarkers();
        return ResponseEntity.ok(armarkers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armarker> getArmarkerById(@PathVariable Integer id) {
        return armarkerService.getArmarkerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Armarker> addArmarker(@RequestBody Armarker armarker) {
        Armarker savedArmarker = armarkerService.addArmarker(armarker);
        return ResponseEntity.ok(savedArmarker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Armarker> updateArmarker(@PathVariable Integer id, @RequestBody Armarker armarker) {
        Armarker updatedArmarker = armarkerService.updateArmarker(id, armarker);
        return ResponseEntity.ok(updatedArmarker);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArmarker(@PathVariable Integer id) {
        armarkerService.deleteArmarker(id);
        return ResponseEntity.ok().build();
    }
}
