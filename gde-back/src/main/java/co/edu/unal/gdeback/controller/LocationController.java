package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Location;
import co.edu.unal.gdeback.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {
    @Autowired
    LocationRepository locationRepository;

    @GetMapping("/location")
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @PostMapping("/location")
    public Location createLocation(@Valid @RequestBody Location location) {
        return locationRepository.save(location);
    }

    @GetMapping("/location/{location_id}")
    public Location getLocationById(@PathVariable(value = "location_id") Integer locationId) {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "LocationId", locationId));
    }

    @DeleteMapping("/location/{location_id}")
    public ResponseEntity<?> deleteLocation(@PathVariable(value = "location_id") Integer locationId) {

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location", "LocationId", locationId));

        locationRepository.delete(location);
        return ResponseEntity.ok().build();
    }
}
