package co.edu.unal.gdeback.model;

import co.edu.unal.keys.User_Interest_Id;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import javax.persistence.EmbeddedId;

@Entity
@Table(name = "user_interest")
@EntityListeners(AuditingEntityListener.class)
public class User_Interest  {
    
    private User_Interest_Id user_interest_Id=new User_Interest_Id();
    private User user_i;
    private Interest interest;
    
    @EmbeddedId
    @JsonIgnore
    public User_Interest_Id getId(){
        return   user_interest_Id;
    }
    
    public void setId(User_Interest_Id user_interest_Id){
        this.user_interest_Id=user_interest_Id;
    }
    
    @MapsId("user_e_mail")
    @ManyToOne
    @JoinColumn(name="user_e_mail", nullable=false,referencedColumnName = "user_e_mail")
    @JsonIgnore
    public User getUser_i(){
        return user_i;
    }
    
    public void setUser_i(User user){
        this.user_i=user;
    }
    
    @MapsId("interest_id")
    @ManyToOne
    @JoinColumn(name="interest_id", nullable=false,referencedColumnName = "interest_id")
    @JsonIgnore
    public Interest getInterest(){
        return interest;
    }
    public void setInterest(Interest interest){
        this.interest=interest;
    }
 
}