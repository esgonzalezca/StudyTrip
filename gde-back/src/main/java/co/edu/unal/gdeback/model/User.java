package co.edu.unal.gdeback.model;

import java.util.Set;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class User {

    public User(String user_e_mail, String user_password) {
        this.user_e_mail = user_e_mail;
        this.user_password = user_password;
    }
   
    public User() {}
    
    @Id
    private String user_e_mail;

    @NotBlank
    private String user_password;
    
    @OneToMany(mappedBy = "user_i")
    private Set<User_Interest> interests;
    
    @OneToMany(mappedBy = "user_g")
    private Set<User_StudyGroup> studyGroup;
    
    public String getUser_e_mail() {
        return user_e_mail;
    }

    public void setUser_e_mail(String user_e_mail) {
        this.user_e_mail = user_e_mail;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    
}