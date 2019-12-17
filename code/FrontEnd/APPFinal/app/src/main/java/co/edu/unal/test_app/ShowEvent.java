package co.edu.unal.test_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.unal.test_app.Services.Event;
import co.edu.unal.test_app.Services.EventService;
import co.edu.unal.test_app.Services.Location;
import co.edu.unal.test_app.Services.User_EventService;
import co.edu.unal.test_app.Services.User_StudyGroupService;
import co.edu.unal.test_app.keys.User_Event_Id;
import co.edu.unal.test_app.keys.User_studyGroup_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_event);
        final int eventIdSelected= getIntent().getIntExtra("idEvent",0);
        final String userEmail= getIntent().getStringExtra("userEmail");
        final TextView eventName = findViewById(R.id.userName);
        final TextView eventDescription = findViewById(R.id.eventDescription);
        final TextView eventTime = findViewById(R.id.eventTime);
        final TextView eventPlace = findViewById(R.id.eventPlace);
        final TextView eventGuest = findViewById(R.id.eventGuest);
        final TextView eventDuration = findViewById(R.id.eventDuration);
        final TextView eventMembers=findViewById(R.id.eventMembers);
        final FloatingActionButton backBtn= findViewById(R.id.btnBackEvent);
        final Button unirse=findViewById(R.id.btnUnirse);
        final ImageView imgEvento=findViewById(R.id.imgEvento1);

        imgEvento.setImageResource(R.drawable.img_evento);

        getSupportActionBar().hide();

        final String EventName = getIntent().getStringExtra("EventName");
        final String PorVista = getIntent().getStringExtra("PorVista");

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        final EventService getService = retrofit.create(EventService.class);
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
            public void onFailure(Call<Event> call, Throwable t) {

            }
        });

        unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PorVista.equals("true")) {
                    mostrarDialogo(userEmail, eventIdSelected, userEmail);
                }else{
                    mostrarDialogo2(userEmail, eventIdSelected, userEmail, EventName);
                }
            }
        });
    }

    public void irAMainMenu(String e_mail){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Email", e_mail);
        startActivity(intent);
    }

    public void mostrarDialogo(final String e_mail, final int groupId, final String email) {

        new AlertDialog.Builder(this).setTitle("Unirse al evento")
                .setMessage("¿Deseas unirte al evento?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        User_EventService getService = retrofit.create(User_EventService.class);
                        User_Event_Id user_event_id=new User_Event_Id(groupId,e_mail);
                        Call< Boolean > call = getService.createUser_Event_Id(user_event_id);
                        call.enqueue(new Callback<Boolean>() {

                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                irAMainMenu(e_mail);
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
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

    public void mostrarDialogo2(final String e_mail, final int groupId, final String email, final String EventName) {

        new AlertDialog.Builder(this).setTitle("Unirse al evento")
                .setMessage("¿Deseas unirte al evento?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        final EventService jsonPlaceHolderApi = retrofit.create(EventService.class);
                        final User_StudyGroupService user_studyGroupService=retrofit.create(User_StudyGroupService.class);
                        final boolean[] itexists = new boolean[1];
                        final int[] idGroup = new int[1];

                        Call< Event > cally = jsonPlaceHolderApi.findEventByName(EventName);
                        cally.enqueue(new Callback<Event>() {
                            @Override
                            public void onResponse(Call<Event> call, Response<Event> response) {

                                idGroup[0] = response.body().getGroup_id();

                                User_studyGroup_Id user_studyGroup_id= new User_studyGroup_Id(MainMenuActivity.userEmail,response.body().getGroup_id());
                                Call<Boolean> callx = user_studyGroupService.exists(user_studyGroup_id);
                                callx.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> responsex) {
                                        itexists[0] =responsex.body();

                                        if (itexists[0]==false) {
                                            User_studyGroup_Id user_studyGroup_id= new User_studyGroup_Id(email, idGroup[0]);//aqui le mando el id del grupo que acabo de crear
                                            Call<Boolean> call2 = user_studyGroupService.createUser_Group(user_studyGroup_id);
                                            call2.enqueue(new Callback<Boolean>() {
                                                @Override
                                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                                }

                                                @Override
                                                public void onFailure(Call<Boolean> call, Throwable t) {//creo que esta parte sirve pa 3 mierdas

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {

                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<Event> call, Throwable t) {

                            }
                        });

                        User_EventService getService = retrofit.create(User_EventService.class);
                        User_Event_Id user_event_id=new User_Event_Id(groupId,e_mail);
                        Call< Boolean > call = getService.createUser_Event_Id(user_event_id);
                        call.enqueue(new Callback<Boolean>() {

                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                irAMainMenu(e_mail);
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
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
