package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Event;
import co.edu.unal.gdeback.model.User_StudyGroup;
import co.edu.unal.gdeback.repository.StudyGroupRepository;
import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.repository.User_GroupRepository;
import co.edu.unal.keys.User_studyGroup_Id;
import java.sql.Types;
import java.util.List;
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
    
    @DeleteMapping("/user_studyGroup")
    @ResponseBody
    public Integer deleteGroupSubsciption (@Valid @RequestBody User_studyGroup_Id user_StudyGroupId){
        String user_e_mail=user_StudyGroupId.getUser_e_mail();
        int group_id= user_StudyGroupId.getStudyGroup_id();
        String sqlA = "delete FROM user_study_group where user_e_mail = ? and group_id=?"; 
        Object[] params = { user_e_mail,group_id };
        int[] types = {Types.VARCHAR, Types.INTEGER};
        int rows= jdbcTemplate.update(sqlA, params, types);
        
    //Esto es para dessuscribirme de  los eventos del grupo del que me acabo de salir
    String sqlD = "select event_id, event_name from study_group natural join event where group_id = ?";
        List<Event> ul2= jdbcTemplate.query(sqlD, new Object[]{group_id}, (rs, rowNum) -> new Event(      
                
                rs.getInt("event_id"),
                rs.getString("event_name")
                ));
        
        if(!ul2.isEmpty()){
            for (int i = 0; i < ul2.size(); i++) {
                String sqlE = "delete FROM user_event where user_e_mail = ? and event_id=?"; 
                
                Object[] params3 = { user_e_mail,ul2.get(i).getEvent_id()};
                int[] types3 = {Types.VARCHAR,Types.INTEGER};
                int rows3= jdbcTemplate.update(sqlE, params3, types3);
                String sqlB = "select count(*) as members from user_event where event_id = ?";
                List<Entero> ul= jdbcTemplate.query(sqlB, new Object[]{ul2.get(i).getEvent_id()}, (rs, rowNum) -> new Entero(
                                rs.getInt("members")
                        ));
                if(ul.get(0).getEntero()==0){
                String sqlH = "select reservation_id from event where event_id = ?";
                List<Event> ul3= jdbcTemplate.query(sqlH, new Object[]{ul2.get(i).getEvent_id()}, (rs, rowNum) -> new Event( 
                    rs.getInt("reservation_id")
                ));
                String sqlF = "delete FROM event where event_id=?"; 
                Object[] params4 = { ul2.get(i).getEvent_id() };
                int[] types4 = { Types.INTEGER};
                int rows4= jdbcTemplate.update(sqlF, params4, types4);
                
                String sqlG = "delete FROM reservation where reservation_id=?";
                Object[] params5 = { ul3.get(0).getReservation_id()};
                int[] types5 = { Types.INTEGER};
                int rows5= jdbcTemplate.update(sqlG, params5, types5);
                }
            //Esto es para ver si ya no quedan miembros entonces vamos a borrar el evento
            }
        }
        //ahora hay que ver si el grupo se queda en ceros para borrarlo. 
        String sqlF = "select count(*) as members from user_study_group where group_id = ?";
        List<Entero> ul= jdbcTemplate.query(sqlF, new Object[]{group_id}, (rs, rowNum) -> new Entero(
                rs.getInt("members")
        ));
        
        if(ul.get(0).getEntero()==0){
            //borrmos primero los anuncios que haya
            String sqlC = "delete FROM announcement where group_id=?"; 
            Object[] params2 = { group_id };
            int[] types2 = { Types.INTEGER};
            int rows2= jdbcTemplate.update(sqlC, params2, types2);
            String sqlH = "delete FROM study_group where group_id=?"; 
            Object[] params3 = { group_id };
            int[] types3 = { Types.INTEGER};
            int rows3= jdbcTemplate.update(sqlH, params3, types3);
        }
        return 1;
    }

    @PostMapping("/user_studyGroup")
    public Boolean createUserStudyGroup(@Valid @RequestBody User_studyGroup_Id user_studyGroupId) {
        
        User_StudyGroup userStudyGroup=new User_StudyGroup();
        
        userStudyGroup.setStudyGroup(studyGroupRepository.findById(user_studyGroupId.getStudyGroup_id())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "GroupId", user_studyGroupId.getStudyGroup_id())));
        
        userStudyGroup.setUser_g(userRepository.findById(user_studyGroupId.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "UserMail", user_studyGroupId.getUser_e_mail())));
        
        userStudyGroup.setId(user_studyGroupId);
        User_StudyGroup myFinal =user_GroupRepository.save(userStudyGroup);
        if(myFinal!=null)return true;
        return false;
    }
    
    @PostMapping("/exists")
    public Boolean exists(@Valid @RequestBody User_studyGroup_Id user_StudyGroupId) {
        
        String user_e_mail=user_StudyGroupId.getUser_e_mail();
        int group_id= user_StudyGroupId.getStudyGroup_id();    
        
        String sqlA = "select * from user_study_group where user_e_mail = ? and group_id=?";
        List<User_studyGroup_Id> ul= jdbcTemplate.query(sqlA, new Object[]{user_e_mail,group_id}, (rs, rowNum) -> new User_studyGroup_Id(
                        rs.getString("user_e_mail"),
                        rs.getInt("group_id")
                ));
        return !ul.isEmpty();
    }
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}