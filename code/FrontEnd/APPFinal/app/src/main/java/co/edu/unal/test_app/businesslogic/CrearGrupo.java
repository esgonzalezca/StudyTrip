package co.edu.unal.test_app.businesslogic;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import co.edu.unal.test_app.MainMenuActivity;
import co.edu.unal.test_app.R;
import co.edu.unal.test_app.Services.Interest;
import co.edu.unal.test_app.Services.InterestService;
import co.edu.unal.test_app.Services.StudyGroup;
import co.edu.unal.test_app.Services.StudyGroupService;
import co.edu.unal.test_app.Services.User_StudyGroupService;
import co.edu.unal.test_app.Utils;
import co.edu.unal.test_app.keys.User_studyGroup_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearGrupo extends AppCompatActivity {

    StudyGroupService studyGroupService;
    User_StudyGroupService user_studyGroupService;
    InterestService interestService;
    Spinner sItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creargestudio);
        final String userEmail = getIntent().getStringExtra("Email");
        final EditText nombre = findViewById(R.id.nombreGrupo);
        final EditText descripcion = findViewById(R.id.descripcionGrupo);
        final FloatingActionButton backBtn= findViewById(R.id.BtnCrearGrupoAtras);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Button crearGrupo=findViewById(R.id.btnCrearGrupo);
        System.out.println("El Email es: "+ userEmail);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        interestService=retrofit.create(InterestService.class);
        studyGroupService=retrofit.create(StudyGroupService.class);
        user_studyGroupService=retrofit.create(User_StudyGroupService.class);
        //lenamos el spinner
        final List<String> spinnerArray =  new ArrayList<String>();

        Call<List<Interest>> callIn = interestService.getInterest();
        callIn.enqueue(new Callback<List<Interest>>() {

            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
               for(Interest interest: response.body()){
                   spinnerArray.add(interest.getInterest_name());
               }
               ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getApplicationContext(), android.R.layout.simple_spinner_item, spinnerArray);
               adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               sItems = (Spinner) findViewById(R.id.selectInterest);
               sItems.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {

                Log.d("MainActivity", t.getMessage());
            }
        });

        crearGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Call< Integer > callIdInterest =  interestService.findIdByName(sItems.getSelectedItem().toString());
                callIdInterest.enqueue(new Callback<Integer>() {

                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        crear(nombre.getText().toString(),descripcion.getText().toString(),
                                response.body(),userEmail,v);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                        Log.d("MainActivity", t.getMessage());
                    }
                });
            }
        });
    }

    private void crear(String nombre, String descripcion, int interes, final String eMail,View v){
        StudyGroup studyGroup=new StudyGroup(nombre,descripcion,interes);
        if (nombre.isEmpty()!=true && descripcion.isEmpty()!=true) {
        Call<StudyGroup> call = studyGroupService.createStudyGroup(studyGroup);
        call.enqueue(new Callback<StudyGroup>() {
            @Override
            public void onResponse(Call<StudyGroup> call, Response<StudyGroup> response) {

                if(response.body().getGroup_name()!=null) {
                    StudyGroup myNewStudyGroup = response.body();
                    User_studyGroup_Id user_studyGroup_id= new User_studyGroup_Id(eMail,myNewStudyGroup.getGroup_id());//aqui le mando el id del grupo que acabo de crear
                    Call<Boolean> call2 = user_studyGroupService.createUser_Group(user_studyGroup_id);
                    call2.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            if(response.body())
                                mostrarExito(eMail);
                            else
                                mostrarFracasoGeneral(eMail);
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            System.out.println(t.getMessage());
                        }
                    });
                }else
                    mostrarErrorYaExiste();
            }

            @Override
            public void onFailure(Call<StudyGroup> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        }else
            Snackbar.make(v, "Por favor, No dejar campos vacios", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

    }
    public void mostrarExito(final String myEmail){

        new AlertDialog.Builder(this).setTitle("Éxito")
                .setMessage("Se ha creado el Grupo de Estudio con éxito")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.putExtra("Email", myEmail);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    public void mostrarFracasoGeneral(final String myEmail){
        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Ha ocurrido un error desconocido")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                        intent.putExtra("Email", myEmail);
                        startActivity(intent);
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public void mostrarErrorYaExiste(){
        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage("Ya existe un Grupo de Estudio con ese nombre")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                }).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    @Override
    public void onBackPressed() {}
}