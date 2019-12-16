package co.edu.unal.test_app.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface User_StudyGroupService {

    @GET("api/user_studyGroup")
    Call<List<User>> getPosts();
}
