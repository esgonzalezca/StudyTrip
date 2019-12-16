package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.User_StudyGroup;
import co.edu.unal.gdeback.repository.StudyGroupRepository;
import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.repository.User_GroupRepository;
import co.edu.unal.keys.User_studyGroup_Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class User_StudyGroupController {

    @Autowired
    User_GroupRepository user_GroupRepository;
    @Autowired
    StudyGroupRepository studyGroupRepository;
     
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/user_studyGroup")
    public User_StudyGroup createUserStudyGroup(@Valid @RequestBody User_studyGroup_Id user_studyGroupId) {
        User_StudyGroup userStudyGroup=new User_StudyGroup();
        
        userStudyGroup.setStudyGroup(studyGroupRepository.findById(user_studyGroupId.getStudyGroup_id())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", user_studyGroupId.getStudyGroup_id())));
        
        userStudyGroup.setUser_g(userRepository.findById(user_studyGroupId.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", user_studyGroupId.getUser_e_mail())));
        
        userStudyGroup.setId(user_studyGroupId);
        System.out.println(userStudyGroup.getUser_g().getUser_e_mail());
        return user_GroupRepository.save(userStudyGroup);
    }
}