package co.edu.unal.test_app.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AnnouncementService {

    @POST("api/announcement")
    Call<Announcement> createAnnouncement(@Body Announcement announcement);

    @GET("api/groupAnnouncements")
    Call<List<Announcement>> getGroupAnnouncements(@Query("group_id") int group_id);

}
