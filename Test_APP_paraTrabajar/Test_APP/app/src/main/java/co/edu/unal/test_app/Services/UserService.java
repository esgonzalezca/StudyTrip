package co.edu.unal.test_app.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    String API_ROUTE = "api/user";

    @GET(API_ROUTE)
    Call< List<User> > getPosts();

    @POST("api/inicio")
    Call<Boolean> VerifIn(@Body User user);

    @POST("api/user")
    Call<Boolean> CreateUser(@Body User user);

}
