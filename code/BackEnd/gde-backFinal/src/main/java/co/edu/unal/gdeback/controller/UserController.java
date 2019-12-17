package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.basics.UserChangePass;
import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.StudyGroup;
import co.edu.unal.gdeback.model.User;
import co.edu.unal.keys.User_studyGroup_Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/user")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    
   @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {
        
        String sqlB = "SELECT count(*) as members FROM user where user_e_mail = ?";
        List<Entero> ul2= jdbcTemplate.query(sqlB, new Object[]{user.getUser_e_mail()}, (rs, rowNum) -> new Entero(
                        rs.getInt("members")   
                ));
        if(ul2.get(0).getEntero()==0){
        
        String sqlA = "SELECT * FROM user where user_e_mail = ?";
        List<User> ul= jdbcTemplate.query(sqlA, new Object[]{user.getUser_e_mail()}, (rs, rowNum) -> new User(
                        rs.getString("user_e_mail"),
                        rs.getString("user_password")
                ));
        return userRepository.save(user);
        }
        return new User();
    }

    
    
    @RequestMapping(value="/inicio")
    @ResponseBody
    public boolean findUserPassword(@Valid @RequestBody User user){
        
        String sqlA = "SELECT * FROM user where user_e_mail = ?";
        String user_e_mail = user.getUser_e_mail();
        String user_password = user.getUser_password();
        List<User> ul= jdbcTemplate.query(sqlA, new Object[]{user_e_mail}, (rs, rowNum) -> new User(
                        rs.getString("user_e_mail"),
                        rs.getString("user_password")
                ));
        User u;
        if (ul.isEmpty()==false){
            u = ul.get(0);
            if (u.getUser_password().equals(user_password)){
                return true;
            }
        }
        return false;
    }

    @GetMapping("/user/{user_e_mail}")
    public User getUserByEmail(@PathVariable(value = "user_e_mail") String userEmail) {
        return userRepository.findById(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userEmail", userEmail));
    }
        
    @PutMapping("/changeUserPass")
    public Boolean changePassword(@Valid @RequestBody UserChangePass userchangePass){
        User user = new User(userchangePass.getUser_e_mail(),userchangePass.getLast_password());
        
        if(findUserPassword(user)){
            User uu = new User(user.getUser_e_mail(),userchangePass.getNew_password());
            userRepository.save(uu);
            return true; 
        }else 
            return false;
    }

    @DeleteMapping("/user")
    @ResponseBody
    public Boolean deletUser (@Valid @RequestBody User user) {
        //antes, verificamos que la contraseña es correcta 
        User realUser=getUserByEmail(user.getUser_e_mail());
        if(realUser.getUser_password().equals(user.getUser_password())){
      
        //ahora nos dessuscribimos de los intereses
        User_InterestController user_InterestController=new User_InterestController();
        user_InterestController.setJdbcTemplate(jdbcTemplate);
        user_InterestController.deleteInterestByUserName(user.getUser_e_mail());

        //después nos dessuscribimos de los grupos, pero antes verificamos si tiene alguno
        String sqlC = "select count(*) as members from user_study_group where user_e_mail = ?";
        List<Entero> ul2= jdbcTemplate.query(sqlC, new Object[]{user.getUser_e_mail()}, (rs, rowNum) -> new Entero(
                        rs.getInt("members")
                ));
        if(ul2.get(0).getEntero()>0){
            StudyGroupController studyGroupController=new StudyGroupController();
            studyGroupController.setJdbcTemplate(jdbcTemplate);
            User_StudyGroupController user_StudyGroupController= new User_StudyGroupController();
            user_StudyGroupController.setJdbcTemplate(jdbcTemplate);
            List<StudyGroup> myStudyGroups=studyGroupController.getMyStudyGroups(user.getUser_e_mail());
            for (int i = 0; i < myStudyGroups.size(); i++) {
                user_StudyGroupController.deleteGroupSubsciption(new User_studyGroup_Id(user.getUser_e_mail(),myStudyGroups.get(i).getGroup_id()));
            }
        }

        //por ultimo borramos el usuario
        User usertoDelete = userRepository.findById(user.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", user.getUser_e_mail()));
        userRepository.delete(user);
        return true;
        }else
            return false;
    }
    
}