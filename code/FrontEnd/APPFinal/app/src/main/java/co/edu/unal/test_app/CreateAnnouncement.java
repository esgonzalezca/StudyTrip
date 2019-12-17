package co.edu.unal.test_app;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.unal.test_app.Services.Announcement;
import co.edu.unal.test_app.Services.AnnouncementService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class CreateAnnouncement extends AppCompatActivity {

    AnnouncementService announcementService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        final int idGroupSelected= getIntent().getIntExtra("idGrupo",0);
        Button publicar = findViewById(R.id.btnCrearAviso);
        final TextView asunto=findViewById(R.id.asunto);
        final TextView cuerpo=findViewById(R.id.cuerpo);
        final FloatingActionButton backBtn= findViewById(R.id.CreateAnn1);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        announcementService=retrofit.create(AnnouncementService.class);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String theasunto = asunto.getText().toString();
                String thecuerpo = cuerpo.getText().toString();

                if (theasunto.isEmpty()==false && thecuerpo.isEmpty()==false) {

                    crearAnuncio(asunto.getText().toString(), cuerpo.getText().toString(), idGroupSelected);

                }else{
                    Snackbar.make(v, "Por favor, no dejar campos vacios", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void crearAnuncio(String asunto, String cuerpo,Integer groupId){
        Announcement announcement=new Announcement(asunto,cuerpo,groupId);
        Call<Announcement> call = announcementService.createAnnouncement(announcement);
        call.enqueue(new Callback<Announcement>() {
            @Override
            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                finish();
            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {}
}