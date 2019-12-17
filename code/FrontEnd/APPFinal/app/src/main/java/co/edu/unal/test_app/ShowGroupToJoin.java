package co.edu.unal.test_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import co.edu.unal.test_app.Services.StudyGroup;
import co.edu.unal.test_app.Services.StudyGroupService;
import co.edu.unal.test_app.Services.User_StudyGroupService;
import co.edu.unal.test_app.keys.User_studyGroup_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowGroupToJoin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_group_to_join);
        final int idGroupSelected= getIntent().getIntExtra("idGroup",0);
        final String email= getIntent().getStringExtra("Email");
        final TextView groupNameLabel = findViewById(R.id.groupNameJ);
        final TextView groupDescriptionLabel = findViewById(R.id.groupDescriptionJ);
        final TextView numberMenbers=findViewById(R.id.NmembersJ);
        final FloatingActionButton backButton= findViewById(R.id.btnBackJ);
        final Button unirse=findViewById(R.id.btnJoinGroup);
        final ImageView imgGrupo=findViewById(R.id.imgGrupo2);

        imgGrupo.setImageResource(R.drawable.img_grupo);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final StudyGroupService getService = retrofit.create(StudyGroupService.class);
        final User_StudyGroupService user_studyGroupService=retrofit.create(User_StudyGroupService.class);
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
                        numberMenbers.setText("N° Usuarios: "+response.body().toString());
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

        unirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_studyGroup_Id user_studyGroup_id= new User_studyGroup_Id(email,idGroupSelected);//aqui le mando el id del grupo que acabo de crear
                Call<Boolean> call2 = user_studyGroupService.createUser_Group(user_studyGroup_id);
                call2.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Ya te uniste al grupo", Toast.LENGTH_LONG);
                        toast1.show();
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {//creo que esta parte sirve pa 3 mierdas
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "Error uniendose al grupo", Toast.LENGTH_LONG);
                        toast1.show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {}
}