package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Interest;
import co.edu.unal.gdeback.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InterestController {
    @Autowired
    InterestRepository interestRepository;

    @GetMapping("/interest")
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    @PostMapping("/interest")
    public Interest createInterest(@Valid @RequestBody Interest interest) {
        return interestRepository.save(interest);
    }

    @GetMapping("/interest/{interest_id}")
    public Interest getInterestById(@PathVariable(value = "interest_id") Integer interestId) {
        return interestRepository.findById(interestId)
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", interestId));
    }

    @DeleteMapping("/interest/{user_e_mail}")
    public ResponseEntity<?> deleteInterest(@PathVariable(value = "interest_id") Integer interestId) {

        Interest interest = interestRepository.findById(interestId)
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", interestId));

        interestRepository.delete(interest);
        return ResponseEntity.ok().build();
    }
    
}
