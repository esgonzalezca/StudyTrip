package co.edu.unal.test_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import co.edu.unal.test_app.Services.User;
import co.edu.unal.test_app.Services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpView extends AppCompatActivity {

    private UserService jsonPlaceHolderApi;
    private Boolean In=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextInputEditText EmailIn = findViewById(R.id.SignEmail);
        final TextInputEditText PasswordIn = findViewById(R.id.SignPassword);
        final TextInputEditText PasswordInConf = findViewById(R.id.SignInconfPassword);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(UserService.class);

        Button createButton = findViewById(R.id.SignB);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                In=false;

                String Email = EmailIn.getText().toString();
                String Password = PasswordIn.getText().toString();
                String PasswordConf = PasswordInConf.getText().toString();

                if (Password.equals(PasswordConf)) {
                    CreateUser(Email, Password, v);
                }
                else{
                    Snackbar.make(v, "las contraseñas no son iguales", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    System.out.println("las contraseñas no son iguales");
                }
            }
        });

    }

    private void CreateUser(final String Email, String Password, final View v) {

        User post = new User(Email,Password);

        Call<Boolean> call = jsonPlaceHolderApi.CreateUser(post);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                In=response.body();

                System.out.println(" respuesta del SignIn: "+response.body());

                if (In==true){
                    openMainMenu(Email);
                }else{
                    Snackbar.make(v, "no se pudo Crear la cuenta, cuenta ya existente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    System.out.println("no se pudo Crear la cuenta, cuenta ya existente");
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    public void openMainMenu(String Email){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Email",Email);
        startActivity(intent);
    }

}
