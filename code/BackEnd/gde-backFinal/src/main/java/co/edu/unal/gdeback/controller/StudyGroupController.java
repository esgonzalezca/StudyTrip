package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.StudyGroup;
import co.edu.unal.gdeback.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
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
    
    @GetMapping("/idByName")
    public Integer findIdByName(@RequestParam(value = "group_name") String group_name) {
        String sqlA = "SELECT * FROM study_group where  group_name = ?";
        List<StudyGroup> ul= jdbcTemplate.query(sqlA, new Object[]{group_name}, (rs, rowNum) -> new StudyGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_description"),
                rs.getString("group_name"),
                rs.getInt("interest_id")
                ));
        return ul.get(0).getGroup_id();
    }
    
    
    @GetMapping("/numbersById")
    public Integer getNumbersById( @RequestParam (value = "group_id") String group_id) {
        String sqlA = "select count( *) as number from user_study_group where group_id = ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{group_id}, (rs, rowNum) -> new Entero(
                        rs.getInt("number")  
                ));
        return ul.get(0).getEntero();
    }

    @GetMapping("/study_group")
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }

    @PostMapping("/study_group")
    //Como no se puede devolver null, lo que vamos a hacer es devolver un grupo sin nombre y sin nada esto significará que ya existe ese y manejarlo como error en el Android
    public StudyGroup createStudyGroup(@Valid @RequestBody StudyGroup studyGroup) {
        String sqlA = "SELECT count(*) as number FROM study_group where group_name= ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{studyGroup.getGroup_name()}, (rs, rowNum) -> new Entero(
                        rs.getInt("number")
                ));
        if(ul.get(0).getEntero()==0)
            return studyGroupRepository.save(studyGroup);
        return new StudyGroup();
    }
    
    @PostMapping("/sg_search")
    public List<StudyGroup> getStudyGroupByKey(@RequestBody String key) {
        key=key.substring(1, key.length()-1);
        key="%"+key+"%";
        String sqlA = "select * from study_group where group_name like ?";
        List<StudyGroup> el= jdbcTemplate.query(sqlA, new Object[]{key}, (rs, rowNum) -> new StudyGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_description"),
                        rs.getString("group_name"),
                        rs.getInt("interest_id")
                ));
        return el;
    }
    
    @PostMapping("/sg_search_Int")
    public List<StudyGroup> getStudyGroupByKeyAndInterest(@RequestBody String[] key) {
        key[0]="%"+key[0]+"%";
        String sqlA = "select * from study_group where group_name like ? AND interest_id="+key[1];
        List<StudyGroup> el= jdbcTemplate.query(sqlA, new Object[]{key[0]}, (rs, rowNum) -> new StudyGroup(
                        rs.getInt("group_id"),
                        rs.getString("group_description"),
                        rs.getString("group_name"),
                        rs.getInt("interest_id")
                ));
        return el;
    }

    @GetMapping("/study_group/{group_id}")
    public StudyGroup getStudyGroupById(@PathVariable(value = "group_id") Integer groupId) {
        return studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", groupId));
    }
    
}