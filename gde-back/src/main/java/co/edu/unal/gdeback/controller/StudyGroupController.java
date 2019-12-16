package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.StudyGroup;
import co.edu.unal.gdeback.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class StudyGroupController {
    @Autowired
    StudyGroupRepository studyGroupRepository;
     @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    
    @GetMapping("/myStudy_group")
    public List<StudyGroup> getMyStudyGroups(@RequestParam(value = "user_e_mail") String userEmail) {
        String sqlA = "SELECT * FROM study_group natural join user_study_group where user_e_mail = ?";
        
        
        List<StudyGroup> ul= jdbcTemplate.query(sqlA, new Object[]{userEmail}, (rs, rowNum) -> new StudyGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_description"),
                rs.getString("group_name"),
                rs.getInt("interest_id")
                ));
        
        return ul;
    }
    
    
    
   
    
    

    @GetMapping("/study_group")
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }
    
    
    

    @PostMapping("/study_group")
    public StudyGroup createStudyGroup(@Valid @RequestBody StudyGroup studyGroup) {
        System.out.println(studyGroup.getGroup_id()+" "+ studyGroup.getGroup_name()+" "+ studyGroup.getGroup_description()+" "+studyGroup.getInterest_id());
                
        StudyGroup studyGroup1=new StudyGroup();
        studyGroup1.setGroup_description(studyGroup.getGroup_description());
        studyGroup1.setGroup_name(studyGroup.getGroup_name());
        Integer myId=studyGroup.getInterest_id();
        
        studyGroup1.setInterest_id(myId);
 
return studyGroupRepository.save(studyGroup1);
    }
    
     @PostMapping("/sg_search")
    public List<StudyGroup> getEventsByKey(@RequestBody String key) {
        key=key.substring(1, key.length()-1);
        key="%"+key+"%";
        String sqlA = "select * from study_group where group_name like ?";
        List<StudyGroup> el= jdbcTemplate.query(sqlA, new Object[]{key}, (rs, rowNum) -> new StudyGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_name"),
                        rs.getString("group_description"),
                        rs.getInt("interest_id")
                ));
        System.out.println(key);
        return el;
    }

    @GetMapping("/study_group/{group_id}")
    public StudyGroup getStudyGroupById(@PathVariable(value = "group_id") Integer groupId) {
        return studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", groupId));
    }

    @PutMapping("/study_group/{group_id}")
    public StudyGroup updateStudyGroup(@PathVariable(value = "group_id") Integer groupId,
                           @Valid @RequestBody StudyGroup groupDetails) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", groupId));

        
        StudyGroup updatedStudyGroup = studyGroupRepository.save(studyGroup);
        return updatedStudyGroup;
    }

    @DeleteMapping("/study_group/{group_id}")
    public ResponseEntity<?> deleteGroup(@PathVariable(value = "group_id") Integer groupId) {

        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", groupId));

        studyGroupRepository.delete(studyGroup);
        return ResponseEntity.ok().build();
    }
    
}
