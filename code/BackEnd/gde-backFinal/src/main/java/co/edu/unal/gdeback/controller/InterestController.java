package co.edu.unal.gdeback.controller;

import co.edu.unal.basics.Entero;
import co.edu.unal.gdeback.model.Interest;
import co.edu.unal.gdeback.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class InterestController {
    
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/findInterestIdByName")
    public Integer findInterestIdByName(@RequestParam(value = "interest_name") String interest_name){
        String sqlA = "SELECT interest_id FROM interest where interest_name= ?";
        List<Entero> ul= jdbcTemplate.query(sqlA, new Object[]{interest_name}, (rs, rowNum) -> new Entero(
                        rs.getInt("interest_id")
                ));
        return ul.get(0).getEntero();
    }
    
    @GetMapping("/interest")
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }
    
}