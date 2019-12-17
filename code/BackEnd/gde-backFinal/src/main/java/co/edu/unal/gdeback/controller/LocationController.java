package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.model.Location;
import co.edu.unal.gdeback.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class LocationController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    LocationRepository locationRepository;
    
    @GetMapping("/idLocationByName")
    public Integer findIdByName(@RequestParam(value = "location_name") String location_name) {
        String sqlA = "SELECT * FROM location where location_name =?";
        List<Location> ul= jdbcTemplate.query(sqlA, new Object[]{location_name}, (rs, rowNum) -> new Location(
                        rs.getInt("location_id"),
                        rs.getString("location_name"),
                rs.getString("location_description")
                ));
        return ul.get(0).getLocation_id();
    }
    
    @GetMapping("/location")
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
    
}