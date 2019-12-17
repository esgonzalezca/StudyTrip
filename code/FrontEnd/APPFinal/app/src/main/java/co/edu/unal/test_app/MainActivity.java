package co.edu.unal.test_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import co.edu.unal.test_app.Services.UserService;
import co.edu.unal.test_app.Services.User;
import co.edu.unal.test_app.dialogs.DropAccountDiaolog;
import co.edu.unal.test_app.dialogs.UtilDialog;
import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import co.edu.unal.test_app.Utils;

public class MainActivity extends AppCompatActivity implements UtilDialog.UtilDialogListener {

    private UserService jsonPlaceHolderApi;
    private Boolean In=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextInputEditText EmailIn = findViewById(R.id.EmailIn);
        final TextInputEditText PasswordIn = findViewById(R.id.PassIn);
        final FloatingActionButton BtnIP= findViewById(R.id.BtnChangeIP);

        BtnIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogIP();

            }
        });

        getSupportActionBar().hide();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(UserService.class);

        Button createButton = findViewById(R.id.LoginB);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                In=false;

                String Email = EmailIn.getText().toString();
                String Password = PasswordIn.getText().toString();
                if (Email.isEmpty() == false && Password.isEmpty() == false)
                    VerifIn(Email,Password, v);
                else
                    Snackbar.make(v, "Por favor, no dejar campos vacios", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });

        Button Sign = findViewById(R.id.CreateEvent);
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSign();
            }
        });
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
                In=response.body();
                if (In==true)
                    openMainMenu(Email);
                else
                    Snackbar.make(v, "no se pudo iniciar sesion, verificar usuario y/o contraseña", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
    @Override
    public void onBackPressed() {
    }

    public void showDialogIP(){
        UtilDialog changeIPDiaolog=new UtilDialog();
        changeIPDiaolog.show(getSupportFragmentManager() ,"cuadro de ejemplo");
    }

    @Override
    public void sentDataForPass(String pass) {

        Utils.setUrl("http://"+pass+"/");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
