package co.edu.unal.test_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import co.edu.unal.test_app.Services.Event;
import co.edu.unal.test_app.Services.EventService;
import co.edu.unal.test_app.Services.Location;
import co.edu.unal.test_app.Services.User_EventService;
import co.edu.unal.test_app.keys.User_Event_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class showEventJoined extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_eventjoined);
        final int eventIdSelected= getIntent().getIntExtra("idEvent",0);
        final String userEmail= getIntent().getStringExtra("userEmail");
        final TextView eventName = findViewById(R.id.eventName2);
        final TextView eventDescription = findViewById(R.id.eventDescription2);
        final TextView eventTime = findViewById(R.id.eventTime2);
        final TextView eventPlace = findViewById(R.id.place2);
        final TextView eventGuest = findViewById(R.id.specialGuest2);
        final TextView eventDuration = findViewById(R.id.Duration2);
        final TextView eventMembers=findViewById(R.id.EmailIn2);
        final FloatingActionButton backBtn= findViewById(R.id.bot2);
        final Button abandonar=findViewById(R.id.btnAbandonar);
        final ImageView imgEvento=findViewById(R.id.imgEvento2);

        imgEvento.setImageResource(R.drawable.img_evento);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        final  EventService getService = retrofit.create(EventService.class);
        Call<Event> call = getService.getEventById(eventIdSelected);
        call.enqueue(new Callback<Event>() {

            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event event = response.body();
                eventName.setText("Nombre: "+event.getEvent_name());
                eventDescription.setText("    "+event.getEvent_description());
                eventGuest.setText("Invitado especial: "+event.getEvent_special_guest());
                eventDuration.setText("Duración: "+event.getEvent_duration().toString()+" minutos");

                Call<Location> call3 = getService.getLocationObject(eventIdSelected);
                call3.enqueue(new Callback<Location>(){

                    @Override
                    public void onResponse(Call<Location> call, Response<Location> response) {
                        Location location=response.body();
                        eventPlace.setText("Lugar: "+location.getLocation_name());
                    }

                    @Override
                    public void onFailure(Call<Location> call, Throwable t) {

                    }
                });

                Call<Integer> call2 = getService.getEventMembersById(eventIdSelected);
                call2.enqueue(new Callback<Integer>() {

                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        eventMembers.setText("Número de asistentes: "+response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("MainActivity", t.getMessage());
                    }
                });

                Call<Integer> call4 = getService.getEventTime(eventIdSelected);
                call4.enqueue(new Callback<Integer>() {

                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        eventTime.setText("Hora: "+response.body().toString()+" horas");
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("MainActivity", t.getMessage());
                    }
                });
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {}
        });

        abandonar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogo(userEmail, eventIdSelected);
            }
        });
    }
    public void irAMainMenu(String e_mail){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Email", e_mail);
        startActivity(intent);
    }

    public void mostrarDialogo(final String e_mail, final int groupId) {

        new AlertDialog.Builder(this).setTitle("Abandonar evento")
                .setMessage("¿Estás seguro de abandonar el evento?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        User_EventService getService = retrofit.create(User_EventService.class);
                        User_Event_Id user_event_id=new User_Event_Id(groupId,e_mail);
                        Call< Integer > call = getService.deleteRegister(user_event_id);
                        call.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                Integer get = response.body();
                                if(get==1)
                                    irAMainMenu(e_mail);
                                else {

                                    new AlertDialog.Builder(getApplicationContext()).setTitle("Error")
                                            .setMessage("No se ha podido abandonar el evento")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {}
                                            }).setIcon(android.R.drawable.ic_secure).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("MainActivity", t.getMessage());
                            }
                        });
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("mensaje","se cancelo la accion");
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
    @Override
    public void onBackPressed() {}

}