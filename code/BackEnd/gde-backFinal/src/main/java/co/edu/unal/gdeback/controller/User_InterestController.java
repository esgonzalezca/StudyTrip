package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.basics.UserChangeInterest;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Interest;
import co.edu.unal.gdeback.model.User;
import co.edu.unal.gdeback.model.User_Interest;
import co.edu.unal.gdeback.repository.InterestRepository;
import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.repository.User_InterestRepository;
import co.edu.unal.keys.User_Interest_Id;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class User_InterestController {
    
    @Autowired
    User_InterestRepository user_InterestRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;
        
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PutMapping("/user_interest")
    public Boolean changeInterests(@Valid @RequestBody UserChangeInterest userChangeInterest){
        
        String sqlA = "delete FROM user_interest where user_e_mail = ?"; 
        Object[] params = { userChangeInterest.getUser_e_mail()};
        int[] types = {Types.VARCHAR};
        int rows= jdbcTemplate.update(sqlA, params, types);
        User user= userRepository.findById(userChangeInterest.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_e_mail", userChangeInterest.getUser_e_mail()));
        Interest interest=  interestRepository.findById(userChangeInterest.getInterest_id1())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "interest_id", userChangeInterest.getInterest_id1()));
        Interest interest2=  interestRepository.findById(userChangeInterest.getInterest_id2())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "interest_id", userChangeInterest.getInterest_id2()));
        user_InterestRepository.save(new User_Interest(user,interest));
        user_InterestRepository.save(new User_Interest(user,interest2));
        return true ;
    }
    
    public void deleteInterestByUserName ( String user_e_mail) {
        String sqlA = "delete FROM user_interest where user_e_mail = ?";
        Object[] params = {user_e_mail};
        int[] types = {Types.VARCHAR};
        int rows= jdbcTemplate.update(sqlA, params, types);
    }
    
    @PostMapping("/user_interest")
    public Boolean createUserInterest(@Valid @RequestBody User_Interest_Id userInterestId) {
        User_Interest userInterest=new User_Interest();
        
        userInterest.setInterest(interestRepository.findById(userInterestId.getInterest_id())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", userInterestId.getInterest_id())));
        userInterest.setUser_i(userRepository.findById(userInterestId.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", userInterestId.getUser_e_mail())));
        
        userInterest.setId(userInterestId);
        if(userInterest!=null){
             user_InterestRepository.save(userInterest);
             return true;
        }
        return false;
    }
    
    @GetMapping("/userInterests")
    public List<Interest> getMyInterests(@RequestParam(value = "user_e_mail") String userEmail) {
        String sqlA = "SELECT * FROM user_interest natural join interest where user_e_mail = ?";
        List<Interest> ul= jdbcTemplate.query(sqlA, new Object[]{userEmail}, (rs, rowNum) -> new Interest(
                        rs.getInt("interest_id"),
                        rs.getString("interest_name")
                ));
        return ul;
    }
    
    @GetMapping("/userInterestsById")
    public Integer findInterestIdByName(@RequestParam(value = "interest_name") String interest_name){
        String sqlA = "SELECT interest_id FROM interest where interest_name= ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{interest_name}, (rs, rowNum) -> new Entero(
                        rs.getInt("interest_id")
                ));
        return ul.get(0).getEntero();
    }
 
}