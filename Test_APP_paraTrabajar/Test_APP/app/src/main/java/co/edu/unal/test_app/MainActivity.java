package co.edu.unal.test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import co.edu.unal.test_app.Services.UserService;
import co.edu.unal.test_app.Services.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private UserService jsonPlaceHolderApi;
    private Boolean In=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final TextInputEditText EmailIn = findViewById(R.id.EmailIn);
        final TextInputEditText PasswordIn = findViewById(R.id.PassIn);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(UserService.class);

        System.out.println("-----------------Inicio---------------");

        Button createButton = findViewById(R.id.LoginB);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                In=false;

                String Email = EmailIn.getText().toString();
                String Password = PasswordIn.getText().toString();


                VerifIn(Email,Password, v);


            }
        });

        Button Sign = findViewById(R.id.SignB);
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSign();

            }
        });

        /*
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts) {
                    String content = "";
                    content += "email: " + post.getUser_e_mail() + "\n";
                    content += "Password: " + post.getUser_password() + "\n";
                    content += "study: " + post.getUser_study_groups() + "\n";
                    content += "interest ID: " + post.getInterest_id() + "\n";
                    content += "events: " + post.getUser_events() + "\n\n";

                    System.out.println(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        */

        System.out.println("-----------------Fin---------------");
        
    }

    private void VerifIn(final String Email, String Password,final View v) {

        User post = new User(Email,Password);

        Call<Boolean> call = jsonPlaceHolderApi.VerifIn(post);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                System.out.println(" respuesta del inicio: "+response.body());
                In=response.body();

                if (In==true){
                    openMainMenu(Email);
                }else{
                    Snackbar.make(v, "no se pudo iniciar sesion, verificar usuario y/o contraseña", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    System.out.println("no se pudo iniciar sesion");
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

    public void openSign(){
        Intent intent = new Intent(this, SignUpView.class);
        startActivity(intent);
    }


}
