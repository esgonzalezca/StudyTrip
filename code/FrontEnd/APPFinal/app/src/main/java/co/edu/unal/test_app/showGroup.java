package co.edu.unal.test_app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import co.edu.unal.test_app.Services.StudyGroup;
import co.edu.unal.test_app.Services.StudyGroupService;
import co.edu.unal.test_app.Services.User_StudyGroupService;
import co.edu.unal.test_app.keys.User_studyGroup_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.widget.Button;
import retrofit2.converter.gson.GsonConverterFactory;

public class showGroup extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group);
        final int idGroupSelected= getIntent().getIntExtra("idGroup",0);
        final String email= getIntent().getStringExtra("email");
        final TextView groupNameLabel = findViewById(R.id.groupName);
        final TextView groupDescriptionLabel = findViewById(R.id.groupDescription);
        final TextView numberMenbers=findViewById(R.id.numberMembers);
        final FloatingActionButton backButton= findViewById(R.id.bot);
        final FloatingActionButton quitGroup= findViewById(R.id.quitGroup);
        final Button crearAviso=findViewById(R.id.crearAviso);
        final FloatingActionButton  crearEvento=findViewById(R.id.floatingActionButton);
        final Button verEventos=findViewById(R.id.BtnVerEventos);
        final Button verAvisos=findViewById(R.id.btnVerAvisos);
        final ImageView imgGrupo=findViewById(R.id.imgGrupo3);

        imgGrupo.setImageResource(R.drawable.img_grupo);

        crearAviso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CreateAnnouncement.class);
                i.putExtra("idGrupo",idGroupSelected);
                startActivity(i);
            }
        });

        crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CreateEvent.class);
                i.putExtra("idGrupo",idGroupSelected);
                i.putExtra("email",email);
                startActivity(i);
            }
        });

        verEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), VerListaDeEventos.class);
                i.putExtra("idGrupo",idGroupSelected);
                startActivity(i);
            }
        });

        verAvisos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), SeeAnnouncements.class);
                i.putExtra("idGrupo",idGroupSelected);
                startActivity(i);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();
       final  StudyGroupService getService = retrofit.create(StudyGroupService.class);
        Call<StudyGroup> call = getService.getStudyGroupById(idGroupSelected);
        call.enqueue(new Callback<StudyGroup>() {

            @Override
            public void onResponse(Call<StudyGroup> call, Response<StudyGroup> response) {
                StudyGroup studyGroup = response.body();
                groupNameLabel.setText(studyGroup.getGroup_name());
                groupDescriptionLabel.setText(studyGroup.getGroup_description());

                Call<Integer> call2 = getService.getNumberMembers(idGroupSelected);
                call2.enqueue(new Callback<Integer>() {

                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        numberMenbers.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("MainActivity", t.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<StudyGroup> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        quitGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mostrarDialogo(email, idGroupSelected);
            }

        });
    }

    public void irAMainMenu(String e_mail){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("Email", e_mail);
        startActivity(intent);
    }
    public void mostrarDialogo(final String e_mail, final int groupId) {

        new AlertDialog.Builder(this).setTitle("Dejar de seguir grupo")
                .setMessage("¿Estás seguro de dejar de ser parte del grupo?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Utils.url)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        User_StudyGroupService getService = retrofit.create(User_StudyGroupService.class);
                        User_studyGroup_Id user_studyGroup_id=new User_studyGroup_Id(e_mail, groupId);
                        Call< Integer > call = getService.deleteRegister(user_studyGroup_id);
                        call.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                Integer get = response.body();
                                if(get==1)
                                    irAMainMenu(e_mail);
                                else {

                                    new AlertDialog.Builder(getApplicationContext()).setTitle("Error")
                                            .setMessage("No se ha podido eliminar el grupo")
                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            }).setIcon(android.R.drawable.ic_secure).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("MainActivity", t.getMessage());
                            }
                        });
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("mensaje","se cancelo la accion");
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    @Override
    public void onBackPressed() {}

}