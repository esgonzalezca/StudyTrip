package co.edu.unal.test_app.businesslogic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import co.edu.unal.test_app.MainActivity;
import co.edu.unal.test_app.MainMenuActivity;
import co.edu.unal.test_app.R;
import co.edu.unal.test_app.Services.StudyGroup;
import co.edu.unal.test_app.Services.StudyGroupService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearGrupo extends AppCompatActivity {

    StudyGroupService studyGroupService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creargestudio);

        final EditText nombre = findViewById(R.id.nombreGrupo);
        final EditText tema = findViewById(R.id.temaGrupo);
        final EditText descripcion = findViewById(R.id.descripcionGrupo);
        final EditText interesText = findViewById(R.id.interes);
        final String userEmail = getIntent().getStringExtra("Email");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        studyGroupService=retrofit.create(StudyGroupService.class);

        Button crearGrupo=findViewById(R.id.LoginB);
        crearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //crear(nombre.getText().toString(),descripcion.getText().toString(),Integer.parseInt(tema.getText().toString()));
                crear(nombre.getText().toString(),descripcion.getText().toString(),Integer.parseInt(interesText.getText().toString()));
            }
        });

    }

    private void crear(String nombre, String descripcion, int interes){
        Integer a=10;
        int b=1;
        //StudyGroup studyGroup=new StudyGroup(500,nombre,descripcion,interes);
        StudyGroup studyGroup=new StudyGroup(a,nombre,descripcion,interes);
        System.out.println(studyGroup.getGroup_id()+" "+studyGroup.getGroup_interest()+" "+
                studyGroup.getGroup_description()+" "+studyGroup.getGroup_name());
        Call<StudyGroup> call = studyGroupService.createStudyGroup(studyGroup);

        call.enqueue(new Callback<StudyGroup>() {
            @Override
            public void onResponse(Call<StudyGroup> call, Response<StudyGroup> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                finish();
            }

            @Override
            public void onFailure(Call<StudyGroup> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}
