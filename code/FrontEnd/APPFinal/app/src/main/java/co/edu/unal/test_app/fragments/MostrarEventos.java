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

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import co.edu.unal.test_app.MainMenuActivity;
import co.edu.unal.test_app.R;
import co.edu.unal.test_app.Services.EventService;
import co.edu.unal.test_app.Services.Event;
import co.edu.unal.test_app.Utils;
import co.edu.unal.test_app.showEventJoined;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MostrarEventos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MostrarEventos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MostrarEventos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vista;

    private OnFragmentInteractionListener mListener;

    public MostrarEventos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MostrarEventos.
     */
    // TODO: Rename and change types and number of parameters
    public static MostrarEventos newInstance(String param1, String param2) {
        MostrarEventos fragment = new MostrarEventos();
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

        final ArrayAdapter arrayAdapter = new ArrayAdapter( getActivity()   ,android.R.layout.simple_list_item_1,titles);
        vista=inflater.inflate(R.layout.fragment_mostrar_eventos, container, false);
        final ScrollView mainScrollE = (ScrollView)vista.findViewById(R.id.ScrollMisEventos);

        mainScrollE.smoothScrollTo(0,0);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        final EventService postService = retrofit.create(EventService.class);
        Call<List<Event>> call = postService.getMyEvents(MainMenuActivity.userEmail);
        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                arrayAdapter.clear();
                if (response.body().isEmpty()==false) {
                    for (Event post : response.body())
                        titles.add(post.getEvent_name());
                }else
                    titles.add("No se encuentra registrado en ningun evento");
                final ListView lv=vista.findViewById(R.id.listX);
                lv.setFocusable(false);
                lv.setAdapter(arrayAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String item = lv.getItemAtPosition(i).toString();

                        if (item.equals("No se encuentra registrado en ningun evento") == false){

                            Call<Integer> call = postService.findEventIdByName(item);
                        call.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                final int selectedId;
                                selectedId = response.body();
                                Intent intent = new Intent(getActivity(), showEventJoined.class);
                                intent.putExtra("idEvent", selectedId);
                                intent.putExtra("userEmail", MainMenuActivity.userEmail);
                                startActivity(intent);
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
                Log.d("MainActivity", t.getMessage());
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