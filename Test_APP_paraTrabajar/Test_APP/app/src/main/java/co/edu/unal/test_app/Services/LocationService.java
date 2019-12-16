package co.edu.unal.test_app.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationService {

    @GET("api/location")
    Call<List<User>> getPosts();
}
