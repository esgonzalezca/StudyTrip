package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Announcement;
import co.edu.unal.gdeback.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AnnouncementController {
    
    @Autowired
    AnnouncementRepository announcementRepository;

    @GetMapping("/announcement")
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @PostMapping("/announcement")
    public Announcement createAnnouncement(@Valid @RequestBody Announcement announcement) {
        return announcementRepository.save(announcement);
    }
    
    //
    /*@PostMapping("/announcement")
    public Announcement createAnnouncement(@Valid @RequestBody Announcement announcement) {
        Announcement a=new Announcement();
        
        a.set(interestRepository.findById(userInterestId.getInterest_id())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", userInterestId.getInterest_id())));
        userInterest.setUser_i(userRepository.findById(userInterestId.getUser_e_mail())
                .orElseThrow(() -> new ResourceNotFoundException("Interest", "InterestId", userInterestId.getUser_e_mail())));
        
        userInterest.setId(userInterestId);
        //System.out.println(userInterest.getUser().getUser_e_mail());
        return user_InterestRepository.save(userInterest);
    }*/
    
    //

    @GetMapping("/announcement/{announcement_id}")
    public Announcement getAnnouncementById(@PathVariable(value = "announcement_id") Integer announcementId) {
        return announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement", "AnnouncementId", announcementId));
    }

    @DeleteMapping("/announcement/{announcement_id}")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable(value = "group_id") Integer announcementId) {

        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ResourceNotFoundException("Announcement", "AnnouncementId", announcementId));

        announcementRepository.delete(announcement);
        return ResponseEntity.ok().build();
    }
    
}
