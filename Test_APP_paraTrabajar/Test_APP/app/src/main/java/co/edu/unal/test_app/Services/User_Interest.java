package co.edu.unal.test_app.Services;

import co.edu.unal.test_app.keys.User_Interest_Id;

public class User_Interest {

    private User_Interest_Id user_interest_Id;
    private User user_i;
    private Interest interest;

    public User_Interest_Id getUser_interest_Id() {
        return user_interest_Id;
    }

    public void setUser_interest_Id(User_Interest_Id user_interest_Id) {
        this.user_interest_Id = user_interest_Id;
    }

    public User getUser_i() {
        return user_i;
    }

    public void setUser_i(User user_i) {
        this.user_i = user_i;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }
}
