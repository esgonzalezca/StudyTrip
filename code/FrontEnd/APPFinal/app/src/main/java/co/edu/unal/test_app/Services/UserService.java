package co.edu.unal.test_app.Services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {

    @POST("api/inicio")
    Call<Boolean> VerifIn(@Body User user);

    @POST("api/user")
    Call<User> CreateUser(@Body User user);

    @PUT("api/changeUserPass")
    Call<Boolean> changePass(@Body UserChangePass userChangePass);

    @HTTP(method = "DELETE", path = "api/user", hasBody = true)
    Call<Boolean> deleteUser(@Body User user);

}