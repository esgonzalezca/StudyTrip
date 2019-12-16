package co.edu.unal.test_app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import co.edu.unal.test_app.Services.StudyGroup;
import co.edu.unal.test_app.Services.StudyGroupService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import co.edu.unal.test_app.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link buscar_grupo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link buscar_grupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class buscar_grupo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View vista;

    private OnFragmentInteractionListener mListener;

    public buscar_grupo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment buscar_grupo.
     */
    // TODO: Rename and change types and number of parameters
    public static buscar_grupo newInstance(String param1, String param2) {
        buscar_grupo fragment = new buscar_grupo();
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
        vista=inflater.inflate(R.layout.fragment_buscar_grupo, container, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.104:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         StudyGroupService jsonPlaceHolderApi = retrofit.create(StudyGroupService.class);

        Call<List<StudyGroup>> call = jsonPlaceHolderApi.searchGroup("Los patos");

        call.enqueue(new Callback<List<StudyGroup>>() {
            @Override
            public void onResponse(Call<List<StudyGroup>> call, Response<List<StudyGroup>> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                System.out.println("entro, resultado: "+response.body());

                List<StudyGroup> posts = response.body();

                for (StudyGroup post : posts) {
                    titles.add(post.getGroup_name());
                    String content = "";
                    content += "name: " + post.getGroup_name() + "\n";
                    content += "description: " + post.getGroup_description() + "\n";
                    content += "interest: " + post.getGroup_interest() + "\n";

                    System.out.println(content);
                }
                final ListView lv=vista.findViewById(R.id.list);
                lv.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<List<StudyGroup>> call, Throwable t) {
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
