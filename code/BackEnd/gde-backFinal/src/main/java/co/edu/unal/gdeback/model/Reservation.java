package co.edu.unal.gdeback.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    public Reservation(Integer location_id, Integer day, Integer hour) {
        this.location_id = location_id;
        this.day = day;
        this.hour = hour;
    }

    public Reservation() {
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservation_id;
     
     @NotNull
     private Integer location_id;

    public Integer getLocation_id() {
        return location_id;
    }

    public void setLocation_id(Integer location_id) {
        this.location_id = location_id;
    }
    
    @ManyToOne
    @JoinColumn(name="location_id", nullable=false, updatable = false, insertable = false)
    private Location location_r;

    @OneToOne(mappedBy = "reservation_e")
    private Event event;
    
    @NotNull
    private Integer day;
      
    @NotNull
    private Integer hour;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getReservation_id() {
        return reservation_id;
    }
   
}
