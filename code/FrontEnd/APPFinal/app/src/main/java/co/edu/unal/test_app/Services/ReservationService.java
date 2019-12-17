package co.edu.unal.test_app.Services;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservationService {

    @POST("api/verifyReservation")
    Call<Boolean> verifyReservation(@Body Reservation reservation);

    @POST("api/reservation")
    Call<Reservation> reservate(@Body Reservation reservation);
}
