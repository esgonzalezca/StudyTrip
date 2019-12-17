package co.edu.unal.test_app;

import androidx.appcompat.app.AppCompatActivity;
import co.edu.unal.test_app.Services.Event;
import co.edu.unal.test_app.Services.EventService;
import co.edu.unal.test_app.Services.User_EventService;
import co.edu.unal.test_app.keys.User_Event_Id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VerListaDeEventos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_lista_de_eventos);
        final int idGroupSelected= getIntent().getIntExtra("idGrupo",0);
        final ArrayList<String> titles = new ArrayList<>();
        final ListView[] lv = new ListView[1];
        final int[] selectedId = new int[1];
        final ArrayAdapter arrayAdapter = new ArrayAdapter( this ,android.R.layout.simple_list_item_1,titles);
        final FloatingActionButton backBtn= findViewById(R.id.BtnAtrasEv2);
        final ScrollView mainScrollView = (ScrollView)findViewById(R.id.ScrollViewEventos2);

        mainScrollView.smoothScrollTo(0,0);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EventService postService = retrofit.create(EventService.class);
        final User_EventService user_eventService=retrofit.create(User_EventService.class);
        Call<List<Event>> call = postService.getGroupEvents(idGroupSelected);
        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                arrayAdapter.clear();
                for(Event post : response.body())
                    titles.add(post.getEvent_name());
                lv[0] =findViewById(R.id.list3);
                lv[0].setAdapter(arrayAdapter);
                lv[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String item =  lv[0].getItemAtPosition(i).toString();
                        Call< Integer > call = postService.findEventIdByName(item);
                        call.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                selectedId[0] =response.body();
                                User_Event_Id user_event_id= new User_Event_Id(selectedId[0],MainMenuActivity.userEmail);
                                Call<Boolean> callx = user_eventService.exists(user_event_id);
                                callx.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        final Boolean rta=response.body();
                                        if(rta){//está en el grupo
                                            Intent i=new Intent(getApplicationContext(), showEventJoined.class);
                                            i.putExtra("userEmail", MainMenuActivity.userEmail);
                                            i.putExtra("idEvent",selectedId[0] );
                                            startActivity(i);
                                        } else{
                                            Intent i=new Intent(getApplicationContext(), ShowEvent.class);
                                            i.putExtra("userEmail", MainMenuActivity.userEmail);
                                            i.putExtra("idEvent",selectedId[0] );
                                            i.putExtra("PorVista","true");
                                            i.putExtra("EventName",item);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("MainActivity", t.getMessage());
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {}
}