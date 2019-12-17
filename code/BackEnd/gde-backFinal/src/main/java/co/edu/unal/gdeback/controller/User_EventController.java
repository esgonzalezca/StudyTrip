package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Event;
import co.edu.unal.gdeback.model.Reservation;
import co.edu.unal.gdeback.model.User_Event;
import co.edu.unal.gdeback.repository.EventRepository;
import co.edu.unal.gdeback.repository.ReservationRepository;
import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.repository.User_EventRepository;
import co.edu.unal.keys.User_Event_Id;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.jdbc.core.JdbcTemplate;


@RestController
@RequestMapping("/api")
public class User_EventController {
    
    @Autowired
    User_EventRepository user_EventRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostMapping("/user_event")
    public Boolean createUserEvent(@Valid @RequestBody User_Event_Id userEventId) {
        User_Event userEvent=new User_Event();
        
        userEvent.setEvent(eventRepository.findById(userEventId.getEvent_id())
                .orElseThrow(() -> new ResourceNotFoundException("Event", "EventId", userEventId.getEvent_id())));
        userEvent.setUser_e(userRepository.findById(userEventId.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("Event", "UserEmail", userEventId.getUser_e_mail())));
        
        userEvent.setId(userEventId);
         user_EventRepository.save(userEvent);
         if(userEvent!=null) return true;
                 return false;
    }
    
    @DeleteMapping("/user_event")
    @ResponseBody
    public Integer deleteEventSubsciption (@Valid @RequestBody User_Event_Id user_EventId){
        String user_e_mail=user_EventId.getUser_e_mail();
        int event_id= user_EventId.getEvent_id();
        
        String sqlA = "delete FROM user_event where user_e_mail = ? and event_id=?"; 
        Object[] params = { user_e_mail,event_id };
        int[] types = {Types.VARCHAR, Types.INTEGER};
        int rows= jdbcTemplate.update(sqlA, params, types);
        
        String sqlB = "select count(*) as members from user_event where event_id = ?";
        List<Entero> ul= jdbcTemplate.query(sqlB, new Object[]{event_id}, (rs, rowNum) -> new Entero(
                        rs.getInt("members")
                ));
        if(ul.get(0).getEntero()==0){
            Event event = eventRepository.findById(event_id)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "EventId", event_id));
            Reservation reservation= reservationRepository.findById(event.getReservation_id())
            .orElseThrow(() -> new ResourceNotFoundException("Repository", "repository_id", event.getReservation_id()));;
            eventRepository.delete(event);
        }
        return rows;
    }
    
    @PostMapping("/eventExists")
    public Boolean exists(@Valid @RequestBody User_Event_Id user_event_id) {
        
        String user_e_mail=user_event_id.getUser_e_mail();
        int event_id= user_event_id.getEvent_id();    
        
        String sqlA = "select * from user_event where user_e_mail = ? and event_id=?";
        List<User_Event_Id> ul= jdbcTemplate.query(sqlA, new Object[]{user_e_mail,event_id}, (rs, rowNum) -> new User_Event_Id(
                rs.getString("user_e_mail"),
                rs.getInt("event_id")
                ));
        return !ul.isEmpty();
    }
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}