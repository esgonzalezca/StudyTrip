package co.edu.unal.test_app.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationService {

    @GET("api/location")
    Call<List<Location>> getLocations();

    @GET("api/idLocationByName")
    Call<Integer> findIdByName(@Query("location_name")String location_name);
}
