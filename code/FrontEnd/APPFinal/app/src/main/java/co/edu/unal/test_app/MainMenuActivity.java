package co.edu.unal.test_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import co.edu.unal.test_app.Services.InterestService;
import co.edu.unal.test_app.Services.User;
import co.edu.unal.test_app.Services.UserChangeInterest;
import co.edu.unal.test_app.Services.UserChangePass;
import co.edu.unal.test_app.Services.UserService;
import co.edu.unal.test_app.Services.User_InterestService;
import co.edu.unal.test_app.dialogs.ChangeInterestDiaolog;
import co.edu.unal.test_app.dialogs.ChangePassDiaolog;
import co.edu.unal.test_app.dialogs.DropAccountDiaolog;
import co.edu.unal.test_app.fragments.Menu_de_Usuario;
import co.edu.unal.test_app.fragments.MostrarEventos;
import co.edu.unal.test_app.fragments.MostrarGrupos;
import co.edu.unal.test_app.fragments.frangment_buscador;
import co.edu.unal.test_app.ui.main.SectionsPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainMenuActivity extends AppCompatActivity implements Menu_de_Usuario.OnFragmentInteractionListener, MostrarEventos.OnFragmentInteractionListener, MostrarGrupos.OnFragmentInteractionListener, frangment_buscador.OnFragmentInteractionListener, ChangePassDiaolog.ChangePassDialogListener, ChangeInterestDiaolog.ChangeInterestDialogListener, DropAccountDiaolog.DropAccountDialogListener {
public static String userEmail;
private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        System.out.println("url de entrada: "+Utils.url);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userEmail = getIntent().getStringExtra("Email");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void sentDataForPass(String lastPass, String newPass) {

        UserService getService = retrofit.create(UserService.class);

        if (lastPass.isEmpty() == false && newPass.isEmpty() == false){

            Call<Boolean> call = getService.changePass(new UserChangePass(MainMenuActivity.userEmail, lastPass, newPass));
        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body())
                    mostrarCambioExitoso();
                else
                    mostrarIncorrectaPassVieja();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });

    }else{
            mostrarNoNull();
        }

    }
    public void mostrarCambioExitoso(){
        new AlertDialog.Builder(this).setTitle("Realizado")
                .setMessage("Usted ha realizado el cambio de forma exitosa")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }
    public void mostrarNoNull(){
        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Por favor, no dejar campos vacios")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }
    public void mostrarIncorrectaPassVieja(){
        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("La clave antigua no es correcta, intente de nuevo")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    public void mostrarFracasoGeneral(){


        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Ha ocurrido un error desconocido")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.putExtra("Email", MainMenuActivity.userEmail);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    public void mostrarSonIguales(){


        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Los Intereses son iguales, por favor seleccione dos distintos")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    public void mostrarBorradoExitoso(){

        new AlertDialog.Builder(this).setTitle("Exitoso")
                .setMessage("Se ha borrado la cuenta de forma exitosa")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_info) .show();
    }
    public void mostrarPassIncorrecta(){

        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("La clave no es correcta, vuela a intentarlo")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    @Override
    public void sentDataForInterest(final String interest1Name, final String interest2Name) {

        final User_InterestService getService = retrofit.create(User_InterestService.class);
        final InterestService getServiceInterest = retrofit.create(InterestService.class);
        final Call<Integer> call3 = getServiceInterest.findIdByName(interest1Name);
        call3.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
               final int interest1=response.body();
                Call<Integer> call4 = getServiceInterest.findIdByName(interest2Name);
                call4.enqueue(new Callback<Integer>() {

                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        final int interest2=response.body();
                        if(interest1!=interest2) {
                            Call<Boolean> call1 = getService.changeInterests(new UserChangeInterest(MainMenuActivity.userEmail, interest1, interest2));
                            call1.enqueue(new Callback<Boolean>() {

                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (response.body())
                                        mostrarCambioExitoso();
                                     else
                                        mostrarFracasoGeneral();
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Log.d("MainActivity", t.getMessage());
                                }
                            });
                        }else
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
    }

    @Override
    public void sentDataForPass(String pass) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        UserService getService = retrofit.create(UserService.class);

        if (pass.isEmpty() == false){
        Call< Boolean > call = getService.deleteUser(new User(MainMenuActivity.userEmail,pass));
        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.body())
                    mostrarBorradoExitoso();
                else
                    mostrarPassIncorrecta();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
        }else
            mostrarNoNull();
    }
}