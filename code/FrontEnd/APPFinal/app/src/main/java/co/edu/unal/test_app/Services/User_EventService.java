package co.edu.unal.test_app.Services;

import co.edu.unal.test_app.keys.User_Event_Id;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface User_EventService {

    @HTTP(method = "DELETE", path = "api/user_event", hasBody = true)
    Call<Integer> deleteRegister(@Body User_Event_Id user_event_id);

    @POST("api/user_event")
    Call<Boolean> createUser_Event_Id(@Body User_Event_Id user_event_id);

    @POST("api/eventExists")
    Call< Boolean > exists(@Body User_Event_Id user_event_id);
}