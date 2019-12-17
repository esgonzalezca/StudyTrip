package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Event;
import co.edu.unal.gdeback.model.Location;
import co.edu.unal.gdeback.model.StudyGroup;
import co.edu.unal.gdeback.repository.EventRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class EventController {
    
    @Autowired
    EventRepository eventRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/event")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
    
    @GetMapping("/verifyEvent")
    public Boolean verifyEvent( @RequestParam (value = "event_name") String event_name) {
        String sqlA = "select count( *) as number from event where event_name = ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{event_name}, (rs, rowNum) -> new Entero(
                        rs.getInt("number")
                ));
        return ul.get(0).getEntero()==0;
    }
    
    @GetMapping("/groupEvents")
    public List<Event> getGroupEvents(@RequestParam(value = "group_id") String group_id) {
        String sqlA = "select * from  event where group_id = ?";
        List<Event> ul= jdbcTemplate.query(sqlA, new Object[]{group_id}, (rs, rowNum) -> new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("event_description"),
                rs.getInt("event_duration"),
                rs.getString("event_special_guest"),
                rs.getInt("group_id")
                ));
        return ul;
    }
    
    @GetMapping("/myEvents")
    public List<Event> getMyEvents(@RequestParam(value = "user_e_mail") String userEmail) {
        String sqlA = "select * from  user_event natural join event where user_e_mail = ?";
        List<Event> ul= jdbcTemplate.query(sqlA, new Object[]{userEmail}, (rs, rowNum) -> new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("event_description"),
                rs.getInt("event_duration"),
                rs.getString("event_special_guest"),
                rs.getInt("group_id")
                ));
        return ul;
    }
    
   @GetMapping("/eventMembersById")
    public Integer getEventMembersById( @RequestParam (value = "event_id") String event_id) {
        String sqlA = "select count( *) as members from user_event where event_id = ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{event_id}, (rs, rowNum) -> new Entero(
                        rs.getInt("members")
                ));
        return ul.get(0).getEntero();
    }
    
    @GetMapping("/eventTime")
    public Integer getEventTime( @RequestParam (value = "event_id") String event_id) {
        String sqlA = "select hour from reservation natural join event where event_id= ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{event_id}, (rs, rowNum) -> new Entero(
                        rs.getInt("hour")
                ));
        return ul.get(0).getEntero();
    }
    
    @GetMapping("/locationObject")
    public Location getLocationObject( @RequestParam (value = "event_id") String event_id) {
        String sqlA = "select location_name from event natural join location where event_id = ?";
        List<Location> ul= jdbcTemplate.query(sqlA, new Object[]{event_id}, (rs, rowNum) -> new Location(
                        rs.getString("location_name")
                ));
        return ul.get(0);
    }
    
    @GetMapping("/eventIdByName")
    public Integer findEventIdByName(@RequestParam(value = "event_name") String event_name) {
        String sqlA = "SELECT * FROM event where  event_name = ?";
        List<Event> ul= jdbcTemplate.query(sqlA, new Object[]{event_name}, (rs, rowNum) -> new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("event_description"),
                rs.getInt("event_duration"),
                rs.getString("event_special_guest"),
                rs.getInt("group_id")
                ));
        return ul.get(0).getEvent_id();
    }
    
    @GetMapping("/eventByName")
    public Event findEventByName(@RequestParam(value = "event_name") String event_name) {
        String sqlA = "SELECT * FROM event where  event_name = ?";
        List<Event> ul= jdbcTemplate.query(sqlA, new Object[]{event_name}, (rs, rowNum) -> new Event(
                rs.getInt("event_id"),
                rs.getString("event_name"),
                rs.getString("event_description"),
                rs.getInt("event_duration"),
                rs.getString("event_special_guest"),
                rs.getInt("group_id")
                ));
        if (ul.isEmpty()==false){
            return ul.get(0);
        }
        return null;
    }
    
    @PostMapping("/event")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @GetMapping("/event/{event_id}")
    public Event getEventById(@PathVariable(value = "event_id") Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "EventId", eventId));
    }
    
    @PostMapping("/e_search")
    public List<Event> getEventsByKey(@RequestBody String key) {
        key=key.substring(1, key.length()-1);
        key="%"+key+"%";
        String sqlA = "select * from event where event_name like ?";
        List<Event> el= jdbcTemplate.query(sqlA, new Object[]{key}, (rs, rowNum) -> new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("event_description"),
                        rs.getInt("event_duration"),
                        rs.getString("event_special_guest"),
                        rs.getInt("group_id")
                ));
        return el;
    }
    
    @PostMapping("/e_search_In")
    public List<Event> getEventByKeyAndInterest(@RequestBody String[] key) {
        key[0]="%"+key[0]+"%";
        String sqlA = "select * from event where event_name like ?";
        List<Event> el= jdbcTemplate.query(sqlA, new Object[]{key[0]}, (rs, rowNum) -> new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("event_description"),
                        rs.getInt("event_duration"),
                        rs.getString("event_special_guest"),
                        rs.getInt("group_id")
                ));
        List<StudyGroup> Gl;
        List<Event> Fe = new ArrayList<Event>();
        if (el.isEmpty()==false){
            for (int i=0;i<el.size(); i++){
                sqlA = "select * from study_group where group_id like ?";
                Gl = jdbcTemplate.query(sqlA, new Object[]{ el.get(i).getGroup_id() }, (rs, rowNum) -> new StudyGroup(
                            rs.getInt("group_id"),
                            rs.getString("group_description"),
                            rs.getString("group_name"),
                            rs.getInt("interest_id")
                    ));
                if (Gl.get(0).getInterest_id().toString().equals(key[1])){
                    Fe.add(el.get(i));
                }
            }
        }
        return Fe;
    }
    
}