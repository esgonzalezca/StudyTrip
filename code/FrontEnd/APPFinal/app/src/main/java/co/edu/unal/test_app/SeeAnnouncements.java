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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SeeAnnouncements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_announcements);
        final int idGroupSelected= getIntent().getIntExtra("idGrupo",0);
        final ArrayList<String> titles = new ArrayList<>();
        final ListView[] lv = new ListView[1];
        final ArrayAdapter arrayAdapter = new ArrayAdapter( this ,android.R.layout.simple_list_item_1,titles);
        getSupportActionBar().hide();
        final FloatingActionButton backBtn= findViewById(R.id.btnBackAnn1);
        final ScrollView mainScrollView = (ScrollView)findViewById(R.id.ScrollAnn1);

        mainScrollView.smoothScrollTo(0,0);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final AnnouncementService announcementService = retrofit.create(AnnouncementService.class);
        Call<List<Announcement>> call = announcementService.getGroupAnnouncements(idGroupSelected);
        call.enqueue(new Callback<List<Announcement>>() {

            @Override
            public void onResponse(Call<List<Announcement>> call, Response<List<Announcement>> response) {
                arrayAdapter.clear();
                for(Announcement post : response.body()) {
                    titles.add(post.getAnnouncement_topic()+": "+post.getAnnouncement_body());
                }
                lv[0] =findViewById(R.id.list4);
                lv[0].setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<Announcement>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {}
}
