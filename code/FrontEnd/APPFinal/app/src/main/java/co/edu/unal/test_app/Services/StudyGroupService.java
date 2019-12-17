package co.edu.unal.test_app.Services;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudyGroupService {

    @GET("api/myStudy_group")
    Call<List<StudyGroup>> getMyStudyGroup(@Query("user_e_mail")String user_e_mail);

    @GET("api/idByName")
    Call<Integer> findIdByName(@Query("group_name")String group_name);

    @GET("api/numbersById")
    Call<Integer> getNumberMembers(@Query("group_id") int group_id);

    @POST ("api/study_group")
    Call <StudyGroup>  createStudyGroup(@Body StudyGroup studyGroup);

    @POST("api/sg_search")
    Call<List<StudyGroup>> searchGroup(@Body String key);

    @GET("api/study_group/{group_id}")
    Call<StudyGroup> getStudyGroupById(@Path("group_id") int group_id);

    @POST("api/sg_search_Int")
    Call<List<StudyGroup>> searchGroupWithInt(@Body String[] key);

}
