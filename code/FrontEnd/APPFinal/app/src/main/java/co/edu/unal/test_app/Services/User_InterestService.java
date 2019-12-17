package co.edu.unal.test_app.Services;

import java.util.List;
import co.edu.unal.test_app.keys.User_Interest_Id;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface User_InterestService {

    @POST("api/user_interest")
    Call<Boolean> createUserInterest(@Body User_Interest_Id user_interest_id);

    @PUT("api/user_interest")
    Call<Boolean> changeInterests(@Body UserChangeInterest userChangeInterest);

    @GET("api/userInterests")
    Call<List<Interest>> getMyInterests(@Query("user_e_mail") String user_e_mail);

    @GET("api/userInterestsById")
    Call<Integer> GetUserInterestsById(@Query("interest_name") String interest_name);
}
