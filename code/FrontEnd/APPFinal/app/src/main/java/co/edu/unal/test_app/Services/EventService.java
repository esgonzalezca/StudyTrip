package co.edu.unal.test_app.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EventService {

    @GET("api/myEvents")
    Call<List<Event>> getMyEvents(@Query("user_e_mail")String user_e_mail);

    @GET("api/eventIdByName")
    Call<Integer> findEventIdByName(@Query("event_name")String group_name);

    @GET("api/eventByName")
    Call<Event> findEventByName(@Query("event_name")String event_name);

    @GET("api/event/{event_id}")
    Call<Event> getEventById(@Path("event_id") int event_id);

    @GET("api/eventMembersById")
    Call<Integer> getEventMembersById(@Query("event_id") int event_id);

    @GET("api/locationObject")
    Call<Location> getLocationObject(@Query("event_id") int event_id);

    @GET("api/verifyEvent")
    Call<Boolean> verifyEvent(@Query("event_name") String event_name);

    @POST("api/event")
    Call<Event> createEvent(@Body Event event);

    @GET("api/groupEvents")
    Call<List<Event>> getGroupEvents(@Query("group_id") int group_id);

    @GET("api/eventTime")
    Call<Integer> getEventTime(@Query("event_id") int event_id);

    @POST("api/e_search")
    Call<List<Event>> searchEvent(@Body String key);

    @POST("api/e_search_In")
    Call<List<Event>> searchEventWithInt(@Body String[] key);

}