package co.edu.unal.gdeback.repository;

import co.edu.unal.gdeback.model.User_Interest;
import co.edu.unal.gdeback.model.User_StudyGroup;
import co.edu.unal.keys.User_Interest_Id;
import co.edu.unal.keys.User_studyGroup_Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_GroupRepository extends JpaRepository<User_StudyGroup, User_studyGroup_Id> {}