package co.edu.unal.test_app.Services;

import co.edu.unal.test_app.keys.User_studyGroup_Id;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.HTTP;

public interface User_StudyGroupService {

    @HTTP(method = "DELETE", path = "api/user_studyGroup", hasBody = true)
    Call<Integer> deleteRegister(@Body User_studyGroup_Id user_StudyGroupId);

    @POST("api/user_studyGroup")
    Call<Boolean> createUser_Group(@Body User_studyGroup_Id user_studyGroup_id);

    @POST("api/exists")
    Call< Boolean > exists(@Body User_studyGroup_Id user_studyGroup_id);
}
