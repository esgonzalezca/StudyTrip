package co.edu.unal.test_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import co.edu.unal.test_app.MainMenuActivity;
import co.edu.unal.test_app.R;
import co.edu.unal.test_app.Services.Event;
import co.edu.unal.test_app.Services.EventService;
import co.edu.unal.test_app.Services.User_EventService;
import co.edu.unal.test_app.Services.User_StudyGroupService;
import co.edu.unal.test_app.ShowEvent;
import co.edu.unal.test_app.Tabbed2;
import co.edu.unal.test_app.Utils;
import co.edu.unal.test_app.keys.User_Event_Id;
import co.edu.unal.test_app.showEventJoined;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link buscar_evento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link buscar_evento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_evento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vista;

    private OnFragmentInteractionListener mListener;

    public buscar_evento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buscar_evento.
     */
    // TODO: Rename and change types and number of parameters
    public static buscar_evento newInstance(String param1, String param2) {
        buscar_evento fragment = new buscar_evento();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<String> titles = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ArrayAdapter arrayAdapter = new ArrayAdapter( getActivity()   ,android.R.layout.simple_list_item_1,titles);
        vista=inflater.inflate(R.layout.fragment_buscar_evento, container, false);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final EventService jsonPlaceHolderApi = retrofit.create(EventService.class);
        final User_EventService user_eventService=retrofit.create(User_EventService.class);
        final User_StudyGroupService user_studyGroupService=retrofit.create(User_StudyGroupService.class);
        final ScrollView mainScrollG = (ScrollView)vista.findViewById(R.id.ScrollBuscarEvento);

        mainScrollG.smoothScrollTo(0,0);

        String[] arr = {Tabbed2.clave,Tabbed2.id};
        Call<List<Event>> call;
        if (Tabbed2.type.equals("1"))
            call = jsonPlaceHolderApi.searchEvent( Tabbed2.clave );
        else
            call = jsonPlaceHolderApi.searchEventWithInt(arr);

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                arrayAdapter.clear();
                if (!response.isSuccessful())
                    return;

                List<Event> posts = response.body();

                if (posts.isEmpty()==false) {
                    for (Event post : posts)
                        titles.add(post.getEvent_name());
                }else
                    titles.add("ningun nombre coincide o posee la palabra clave propuesta");
                final ListView lv=vista.findViewById(R.id.list);
                lv.setFocusable(false);
                lv.setAdapter(arrayAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        final String item = lv.getItemAtPosition(i).toString();

                        if (item.equals("ningun nombre coincide o posee la palabra clave propuesta") == false){

                            System.out.println("grupo: " + item);
                        Call<Integer> call = jsonPlaceHolderApi.findEventIdByName(item);
                        call.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                final int selectedId;
                                selectedId = response.body();
                                User_Event_Id user_event_id = new User_Event_Id(selectedId, MainMenuActivity.userEmail);
                                Call<Boolean> callx = user_eventService.exists(user_event_id);
                                callx.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        final Boolean rta = response.body();
                                        if (rta) {//está en el evento
                                            Intent i = new Intent(getContext(), showEventJoined.class);
                                            i.putExtra("userEmail", MainMenuActivity.userEmail);
                                            i.putExtra("idEvent", selectedId);
                                            startActivity(i);
                                        } else {
                                            final Intent i = new Intent(getContext(), ShowEvent.class);
                                            i.putExtra("userEmail", MainMenuActivity.userEmail);
                                            i.putExtra("idEvent", selectedId);
                                            i.putExtra("PorVista", "false");
                                            i.putExtra("EventName", item);
                                            startActivity(i);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Boolean> call, Throwable t) {}
                                });
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Log.d("MainActivity", t.getMessage());
                            }
                        });
                    }
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

