package co.edu.unal.gdeback.repository;

import co.edu.unal.gdeback.model.User_Interest;
import co.edu.unal.keys.User_Interest_Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_InterestRepository extends JpaRepository<User_Interest, User_Interest_Id> {}