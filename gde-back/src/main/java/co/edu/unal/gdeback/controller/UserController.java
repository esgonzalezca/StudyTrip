package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public Boolean createUser(@Valid @RequestBody User user) {
        String sqlA = "SELECT * FROM user where user_e_mail = ?";
         List<User> ul= jdbcTemplate.query(sqlA, new Object[]{user.getUser_e_mail()}, (rs, rowNum) -> new User(
                        rs.getString("user_e_mail"),
                        rs.getString("user_password")
                ));
        if (ul.isEmpty()==true){
        userRepository.save(user);
        return true;
        }
        return false;
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
        
        User u = new User();
        
        if (ul.isEmpty()==false){
        u = ul.get(0);
       
        if (u.getUser_password().equals(user_password)){
            return true;
        }else{
           System.out.println("no existe dicho usuario o contraseña no encontrada");
       }
        
        }
        return false;
    }

    @GetMapping("/user/{user_e_mail}")
    public User getUserByEmail(@PathVariable(value = "user_e_mail") String userEmail) {
        return userRepository.findById(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userEmail", userEmail));
    }

    @PutMapping("/user/{user_e_mail}")
    public User updateUser(@PathVariable(value = "user_e_mail") String userEmail,
                           @Valid @RequestBody User userDetails) {

        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userEmail", userEmail));

        user.setUser_e_mail(userDetails.getUser_e_mail());
        user.setUser_password(userDetails.getUser_password());
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/user/{user_e_mail}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "user_e_mail") String userEmail) {

        User user = userRepository.findById(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userEmail));

        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
    
    @RequestMapping(value="/puto")
    @ResponseBody
    public List<User> findUserId3(@RequestParam("user_password") String user_password) {

        String sql = "SELECT * FROM user where user_password = ?";

        return jdbcTemplate.query(sql, new Object[]{user_password}, (rs, rowNum) -> new User(
                        rs.getString("user_e_mail"),
                        rs.getString("user_password")
                ));
    }
    
    @RequestMapping(value = "/id")
    String getIdByValue(@RequestParam("id") String personId){
        System.out.println("ID is "+personId);
        return "Get ID from query string of URL with value element";
    }
    
    
}