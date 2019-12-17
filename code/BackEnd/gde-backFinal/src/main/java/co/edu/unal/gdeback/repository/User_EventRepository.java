package co.edu.unal.gdeback.repository;

import co.edu.unal.gdeback.model.User_Event;
import co.edu.unal.keys.User_Event_Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_EventRepository extends JpaRepository<User_Event, User_Event_Id> {}