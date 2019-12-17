package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.model.Announcement;
import co.edu.unal.gdeback.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@RestController
@RequestMapping("/api")
public class AnnouncementController {
    
    @Autowired
    AnnouncementRepository announcementRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @GetMapping("/announcement")
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @PostMapping("/announcement")
    public Announcement createAnnouncement(@Valid @RequestBody Announcement announcement) {
        return announcementRepository.save(announcement);
    }
    
    @GetMapping("/groupAnnouncements")
    public List<Announcement> getGroupAnnouncements(@RequestParam(value = "group_id") String group_id) {
        String sqlA = "select * from  announcement where group_id = ?";
        List<Announcement> ul= jdbcTemplate.query(sqlA, new Object[]{group_id}, (rs, rowNum) -> new Announcement(
                rs.getString("announcement_topic"),
                rs.getString("announcement_body")
                ));
        return ul;
    }
    
}