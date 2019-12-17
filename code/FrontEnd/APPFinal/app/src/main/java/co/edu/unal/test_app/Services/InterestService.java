package co.edu.unal.test_app.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InterestService {

    @GET("api/interest")
    Call<List<Interest>> getInterest();

    @GET("api/findInterestIdByName")
    Call<Integer> findIdByName(@Query("interest_name") String interest_name);
}
