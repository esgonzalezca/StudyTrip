package co.edu.unal.test_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;
import co.edu.unal.test_app.Services.Interest;
import co.edu.unal.test_app.Services.InterestService;
import co.edu.unal.test_app.Services.User;
import co.edu.unal.test_app.Services.UserService;
import co.edu.unal.test_app.Services.User_InterestService;
import co.edu.unal.test_app.keys.User_Interest_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpView extends AppCompatActivity {

    private UserService jsonPlaceHolderApi;
    private Boolean In=false;
    private InterestService interestService;
    Spinner sItems;
    Spinner sItems2;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final TextInputEditText EmailIn = findViewById(R.id.SignEmail);
        final TextInputEditText PasswordIn = findViewById(R.id.SignPassword);
        final TextInputEditText PasswordInConf = findViewById(R.id.SignInconfPassword);
        final FloatingActionButton backBtn= findViewById(R.id.BtnCrearUsuarioAtras);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(UserService.class);

        final List<String> spinnerArray =  new ArrayList<String>();

    interestService= retrofit.create(InterestService.class);


        Call<List<Interest>> callIn = interestService.getInterest();

        callIn.enqueue(new Callback<List<Interest>>() {

            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                for(Interest interest: response.body())
                    spinnerArray.add(interest.getInterest_name());

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sItems = (Spinner) findViewById(R.id.spinner1);
                sItems.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });

        final List<String> spinnerArray2 =  new ArrayList<String>();
        Call<List<Interest>> callIn2 = interestService.getInterest();
        callIn2.enqueue(new Callback<List<Interest>>() {

            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                for(Interest interest: response.body())
                    spinnerArray2.add(interest.getInterest_name());

                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray2);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sItems2 = (Spinner) findViewById(R.id.spinner2);
                sItems2.setAdapter(adapter2);
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }

        });

        Button createButton = findViewById(R.id.CreateEvent);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                In=false;
                final String Email = EmailIn.getText().toString();
                final String Password = PasswordIn.getText().toString();
                String PasswordConf = PasswordInConf.getText().toString();

                if (Email.isEmpty() == false && Password.isEmpty() == false && PasswordConf.isEmpty() == false ) {
                    if (Password.equals(PasswordConf) && Email.endsWith("@unal.edu.co")) {
                        Call<Integer> callIdInterest1 = interestService.findIdByName(sItems.getSelectedItem().toString());
                        callIdInterest1.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                final Integer id1 = response.body();
                                Call<Integer> callIdInterest2 = interestService.findIdByName(sItems2.getSelectedItem().toString());
                                callIdInterest2.enqueue(new Callback<Integer>() {

                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (id1 != response.body())
                                            CreateUser(Email, Password, v, id1, response.body());
                                        else
                                            mostrarSonIguales();
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {
                                        Log.d("MainActivity", t.getMessage());
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("MainActivity", t.getMessage());
                            }
                        });
                    } else if(Password.equals(PasswordConf)==false && Email.endsWith("@unal.edu.co")){
                        Snackbar.make(v, "las contraseñas no son iguales", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if(Password.equals(PasswordConf) && Email.endsWith("@unal.edu.co")==false){
                        Snackbar.make(v, "El correo debe ser Institucional", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else if (Password.equals(PasswordConf)==false && Email.endsWith("@unal.edu.co")==false){
                        Snackbar.make(v, "El correo debe ser Institucional y las contraseñas deben ser iguales", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                else
                    Snackbar.make(v, "Por favor, no dejar campos vacios", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }
        });
    }

    private void CreateUser(final String Email, String Password, final View v, final int idInterest1, final int idInterest2) {

        User post = new User(Email,Password);
        Call<User> call = jsonPlaceHolderApi.CreateUser(post);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                final User myNewUser=response.body();
                if (myNewUser==null) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                if (myNewUser.getUser_e_mail()!=null){
                    final User_InterestService user_interestService=retrofit.create(User_InterestService.class);
                    final Call<Boolean> call3 = user_interestService.createUserInterest(new User_Interest_Id(myNewUser.getUser_e_mail(),idInterest1));
                    call3.enqueue(new Callback<Boolean>() {

                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Call<Boolean> call4 = user_interestService.createUserInterest(new User_Interest_Id(myNewUser.getUser_e_mail(),idInterest2));
                            call4.enqueue(new Callback<Boolean>() {

                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if(response.body())
                                        openMainMenu(Email);
                                    else
                                        mostrarErrorGeneral();
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Log.d("MainActivity", t.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d("MainActivity", t.getMessage());
                        }
                    });


                }else
                    Snackbar.make(v, "no se pudo Crear la cuenta, cuenta ya existente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    public void openMainMenu(String Email){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Email",Email);
        startActivity(intent);
    }

    public void mostrarSonIguales(){


        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Los Intereses son iguales, por favor seleccione dos distintos")
                // Spcifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }
    public void mostrarErrorGeneral(){


        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Ha ocurrido un error desconocido")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    @Override
    public void onBackPressed() {}
}