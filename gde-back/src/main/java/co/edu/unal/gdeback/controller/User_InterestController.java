package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.User_Interest;
import co.edu.unal.keys.User_Interest_Id;
import co.edu.unal.gdeback.repository.InterestRepository;
import co.edu.unal.gdeback.repository.UserRepository;
import co.edu.unal.gdeback.repository.User_InterestRepository;
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
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/user_interest")
    public User_Interest createUserInterest(@Valid @RequestBody User_Interest_Id userInterestId) {
        User_Interest userInterest=new User_Interest();
        
        userInterest.setInterest(interestRepository.findById(userInterestId.getInterest_id())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", userInterestId.getInterest_id())));
        userInterest.setUser_i(userRepository.findById(userInterestId.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", userInterestId.getUser_e_mail())));
        
        userInterest.setId(userInterestId);
        //System.out.println(userInterest.getUser().getUser_e_mail());
        return user_InterestRepository.save(userInterest);
    }
}

