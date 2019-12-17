package co.edu.unal.test_app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import co.edu.unal.test_app.MainMenuActivity;
import co.edu.unal.test_app.R;
import co.edu.unal.test_app.Services.StudyGroup;
import co.edu.unal.test_app.Services.StudyGroupService;
import co.edu.unal.test_app.Services.User_StudyGroupService;
import co.edu.unal.test_app.ShowGroupToJoin;
import co.edu.unal.test_app.Utils;
import co.edu.unal.test_app.businesslogic.CrearGrupo;
import co.edu.unal.test_app.keys.User_studyGroup_Id;
import co.edu.unal.test_app.showGroup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MostrarGrupos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MostrarGrupos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MostrarGrupos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button cgb;
    View vista;

    public MostrarGrupos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MostrarGrupos.
     */
    // TODO: Rename and change types and number of parameters

    public static MostrarGrupos newInstance(String param1, String param2) {
        MostrarGrupos fragment = new MostrarGrupos();
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
    ListView lv;
    int selectedId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final ArrayAdapter arrayAdapter = new ArrayAdapter( getActivity()   ,android.R.layout.simple_list_item_1,titles);

        vista=inflater.inflate(R.layout.fragment_mostrar_grupos, container, false);
        final ScrollView mainScrollG = (ScrollView)vista.findViewById(R.id.ScrollMisGrupos);

        mainScrollG.smoothScrollTo(0,0);

        cgb=(Button) vista.findViewById(R.id.createGB);
        cgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), CrearGrupo.class);
                i.putExtra("Email", MainMenuActivity.userEmail);
                startActivity(i);
            }
        });
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();
        final StudyGroupService postService = retrofit.create(StudyGroupService.class);
        final User_StudyGroupService user_studyGroupService=retrofit.create(User_StudyGroupService.class);
        Call< List<StudyGroup> > call = postService.getMyStudyGroup(MainMenuActivity.userEmail);
        call.enqueue(new Callback<List<StudyGroup>>() {

            @Override
            public void onResponse(Call<List<StudyGroup>> call, Response<List<StudyGroup>> response) {
                arrayAdapter.clear();
                if (response.body().isEmpty()==false) {
                    for (StudyGroup post : response.body())
                        titles.add(post.getGroup_name());
                }else
                    titles.add("No forma parte de ningun grupo de estudio");
                lv=vista.findViewById(R.id.list);
                lv.setFocusable(false);
                lv.setAdapter(arrayAdapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        String item = lv.getItemAtPosition(i).toString();

                        if (item.equals("No forma parte de ningun grupo de estudio") == false){
                            Call<Integer> call = postService.findIdByName(item);
                        call.enqueue(new Callback<Integer>() {

                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                selectedId = response.body();
                                User_studyGroup_Id user_studyGroup_id = new User_studyGroup_Id(MainMenuActivity.userEmail, selectedId);
                                Call<Boolean> callx = user_studyGroupService.exists(user_studyGroup_id);
                                callx.enqueue(new Callback<Boolean>() {
                                    @Override
                                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                        final Boolean rta = response.body();
                                        if (rta) {//esta en el grupo
                                            Intent i = new Intent(getContext(), showGroup.class);
                                            i.putExtra("email", MainMenuActivity.userEmail);
                                            i.putExtra("idGroup", selectedId);
                                            startActivity(i);
                                        } else {
                                            Intent i = new Intent(getContext(), ShowGroupToJoin.class);
                                            i.putExtra("Email", MainMenuActivity.userEmail);
                                            i.putExtra("idGroup", selectedId);
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
                    }
                });

            }

            @Override
            public void onFailure(Call<List<StudyGroup>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event


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
