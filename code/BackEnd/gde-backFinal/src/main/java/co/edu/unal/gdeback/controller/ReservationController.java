package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.gdeback.model.Reservation;
import co.edu.unal.gdeback.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/reservation")
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    
    @PostMapping("/reservation")
    public Reservation createReservation(@Valid @RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

   @PostMapping("/verifyReservation")
    public Boolean verifyReservation(@Valid @RequestBody Reservation reservation) {
        String sqlA = "SELECT count(*) as number FROM reservation where location_id = ? and day = ? and hour = ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{reservation.getLocation_id(),reservation.getDay(), reservation.getHour()}, (rs, rowNum) -> new Entero(
                        rs.getInt("number")
                ));
        return ul.get(0).getEntero()==0;
    }
    
}