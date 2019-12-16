package co.edu.unal.test_app.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StudyGroupService {

    @GET("api/myStudy_group")
    Call<List<StudyGroup>> getStudyGroup(@Query("user_e_mail")String user_e_mail);

    @POST ("api/study_group")
    Call <StudyGroup>  createStudyGroup(@Body StudyGroup studyGroup);

    @POST("api/sg_search")
    Call<List<StudyGroup>>  searchGroup(@Body String key);
}
