package co.edu.unal.test_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import co.edu.unal.test_app.Services.Event;
import co.edu.unal.test_app.Services.EventService;
import co.edu.unal.test_app.Services.Location;
import co.edu.unal.test_app.Services.LocationService;
import co.edu.unal.test_app.Services.Reservation;
import co.edu.unal.test_app.Services.ReservationService;
import co.edu.unal.test_app.Services.User_EventService;
import co.edu.unal.test_app.keys.User_Event_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.text.format.DateFormat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CreateEvent extends AppCompatActivity {

    java.util.Date date=new java.util.Date();
    Spinner sItemsPlace;
    Integer idOfPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearevento);

        final int idGroupSelected= getIntent().getIntExtra("idGrupo",0);
        final String email= getIntent().getStringExtra("email");
        Button crearEvento = findViewById(R.id.CreateEvent);
        final EditText name=findViewById(R.id.EventNameLabel);
        final TextView description=findViewById(R.id.eventDescriptionlabel);
        final EditText  duration=findViewById(R.id.DurationLabel);
        final EditText  specialGuest=findViewById(R.id.SpecialGuestLabel);
        final FloatingActionButton backBtn= findViewById(R.id.CrearEventoAtras);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Spinner day=findViewById(R.id.daySpinner);
        Spinner hour=findViewById(R.id.hourSpinner);
        Spinner place=findViewById(R.id.placeSpinner);

        final List<String> spinnerArray =  new ArrayList<String>();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final LocationService getService = retrofit.create(LocationService.class);
        final Call<List<Location>> call = getService.getLocations();
        call.enqueue(new Callback<List<Location>>() {

            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                List<Location> posts = response.body();
                for (Location post : posts) {
                    spinnerArray.add(post.getLocation_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sItemsPlace= (Spinner) findViewById(R.id.placeSpinner);
                sItemsPlace.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {

                Log.d("MainActivity", t.getMessage());
            }
        });

        List<String> spinnerArray2 =  new ArrayList<String>();
        String currentDay= (String) DateFormat.format("dd",date );
        int min=Integer.parseInt(currentDay);
        int max=30;
        for(int i=min+1;i<=max;i++) {
            spinnerArray2.add(String.valueOf(i));

        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray2);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems = (Spinner) findViewById(R.id.daySpinner);
        sItems.setAdapter(adapter2);

        List<String> spinnerArray3 =  new ArrayList<String>();
        spinnerArray3.add("9");
        spinnerArray3.add("13");

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray3);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner sItems2 = (Spinner) findViewById(R.id.hourSpinner);
        sItems2.setAdapter(adapter3);

        //traducir el nombre del lugar a ID
        crearEvento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String namecheck = name.getText().toString();
                String thedescription = description.getText().toString();
                String theduration = duration.getText().toString();
                String theinvitado = specialGuest.getText().toString();

                if (namecheck.isEmpty() == false && thedescription.isEmpty() == false && theduration.isEmpty() == false){

                    final String theQuest;

                    if (theinvitado.isEmpty()==true){
                        theQuest="ninguno";
                    }else{
                        theQuest=theinvitado;
                    }

                    Call<Integer> call3 = getService.findIdByName(sItemsPlace.getSelectedItem().toString());
                call3.enqueue(new Callback<Integer>() {

                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        idOfPlace = response.body();
                        final ReservationService getService2 = retrofit.create(ReservationService.class);

                        Call<Boolean> call2 = getService2.verifyReservation(new Reservation(Integer.parseInt(sItems2.getSelectedItem().toString()), Integer.parseInt(sItems.getSelectedItem().toString()), idOfPlace));
                        call2.enqueue(new Callback<Boolean>() {

                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Boolean respuesta = response.body();
                                if (respuesta) {

                                    final EventService getServiceEvent = retrofit.create(EventService.class);
                                    Call<Boolean> call4 = getServiceEvent.verifyEvent(name.getText().toString());
                                    call4.enqueue(new Callback<Boolean>() {

                                        @Override
                                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                            Boolean myresponde = response.body();
                                            if (myresponde) {
                                                // creamos ahora sí la reservación
                                                Call<Reservation> call5 = getService2.reservate(new Reservation(Integer.parseInt(sItems2.getSelectedItem().toString()), Integer.parseInt(sItems.getSelectedItem().toString()), idOfPlace));
                                                call5.enqueue(new Callback<Reservation>() {

                                                    @Override
                                                    public void onResponse(Call<Reservation> call, Response<Reservation> response) {

                                                        Reservation reservationFinal = response.body();
                                                        if (reservationFinal != null) {
                                                            Call<Event> call6 = getServiceEvent.createEvent(new Event(name.getText().toString(), description.getText().toString(), Integer.parseInt(duration.getText().toString()), theQuest, idGroupSelected, reservationFinal.getReservation_id()));
                                                            call6.enqueue(new Callback<Event>() {

                                                                @Override
                                                                public void onResponse(Call<Event> call, Response<Event> response) {

                                                                    if (response.body() != null) {
                                                                        User_EventService getService4 = retrofit.create(User_EventService.class);
                                                                        Call<Boolean> call7 = getService4.createUser_Event_Id(new User_Event_Id(response.body().getEvent_id(), email));
                                                                        call7.enqueue(new Callback<Boolean>() {

                                                                            @Override
                                                                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                                                if (response.body())
                                                                                    mostrarExito(email);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<Boolean> call, Throwable t) {
                                                                                Log.d("MainActivity", t.getMessage());
                                                                            }
                                                                        });
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<Event> call, Throwable t) {
                                                                    Log.d("MainActivity", t.getMessage());
                                                                }
                                                            });
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Reservation> call, Throwable t) {
                                                        Log.d("MainActivity", t.getMessage());
                                                    }
                                                });
                                            } else
                                                mostrarAlerta2();
                                        }

                                        @Override
                                        public void onFailure(Call<Boolean> call, Throwable t) {
                                            Log.d("MainActivity", t.getMessage());
                                        }
                                    });
                                } else
                                    mostrarAlerta();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Log.d("MainActivity", t.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("MainActivity", t.getMessage());
                    }
                });

            }else{
                    Snackbar.make(view, "Por favor, no dejar vacios los cambos de Nombre, Duracion y descripcion", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

        }
 });
    }
    public void mostrarAlerta(){

        new AlertDialog.Builder(this).setTitle("Ocupado")
                .setMessage("Ese lugar y ese horario ya están ocupados")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public void mostrarAlerta2(){
        new AlertDialog.Builder(this).setTitle("Ocupado")
                .setMessage("Ya existe un evento con ese nombre")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void mostrarExito(final String myEmail){
        new AlertDialog.Builder(this).setTitle("Éxito")
                .setMessage("Se ha creado el evento con éxito")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.putExtra("Email", myEmail);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    @Override
    public void onBackPressed() {}

}