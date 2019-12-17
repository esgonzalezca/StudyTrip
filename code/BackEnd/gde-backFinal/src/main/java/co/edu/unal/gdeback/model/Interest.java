package co.edu.unal.gdeback.model;

import java.util.Set;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "interest")
@EntityListeners(AuditingEntityListener.class)
public class Interest {

    public Interest() {
    }

    public Interest(Integer interest_id, String interest_name) {
        this.interest_id = interest_id;
        this.interest_name = interest_name;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer interest_id;

    @NotBlank
    private String interest_name;

    @OneToMany(mappedBy = "interest_s")
    private Set<StudyGroup> studyGroups;

    @OneToMany(mappedBy = "interest")
    private Set<User_Interest> interests;

    public Integer getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }
     
}
