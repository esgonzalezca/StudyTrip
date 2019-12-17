package co.edu.unal.gdeback.repository;

import co.edu.unal.gdeback.model.Event;
import co.edu.unal.gdeback.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {}