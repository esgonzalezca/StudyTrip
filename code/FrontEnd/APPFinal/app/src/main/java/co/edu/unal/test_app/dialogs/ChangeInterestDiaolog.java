package co.edu.unal.test_app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.ArrayList;
import java.util.List;
import co.edu.unal.test_app.R;
import co.edu.unal.test_app.Services.Interest;
import co.edu.unal.test_app.Services.InterestService;
import co.edu.unal.test_app.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeInterestDiaolog extends AppCompatDialogFragment {

    private Spinner sItems1;
    private Spinner sItems2;
    private ChangeInterestDialogListener listener;
    private InterestService interestService;
    private Retrofit retrofit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= getActivity().getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.layout_dialog_interest,null);
        final List<String> spinnerArray =  new ArrayList<String>();
        retrofit = new Retrofit.Builder()
                .baseUrl(Utils.url)
                .addConverterFactory(GsonConverterFactory.create()).build();

        interestService= retrofit.create(InterestService.class);
        Call<List<Interest>> callIn = interestService.getInterest();
        callIn.enqueue(new Callback<List<Interest>>() {

            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                for(Interest interest: response.body()){
                    spinnerArray.add(interest.getInterest_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sItems1 = (Spinner) view.findViewById(R.id.spinner1);
                sItems1.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }

        });

        final List<String> spinnerArray2 =  new ArrayList<String>();
        Call<List<Interest>> callIn2 = interestService.getInterest();
        callIn2.enqueue(new Callback<List<Interest>>() {

            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                for(Interest interest: response.body()){
                    spinnerArray2.add(interest.getInterest_name());
                }
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_spinner_item, spinnerArray2);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sItems2 = (Spinner) view.findViewById(R.id.spinner2);
                sItems2.setAdapter(adapter2);
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });

        builder.setView(view)
                .setTitle("Ingresar Nuevos Intereses")
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                    }
                })
                .setPositiveButton("cambiar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface , int i){
                listener.sentDataForInterest(sItems1.getSelectedItem().toString(),sItems2.getSelectedItem().toString()); ;
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ChangeInterestDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Listener");
        }
    }

    public interface ChangeInterestDialogListener{
        void sentDataForInterest(String interest1, String interest2);
    }
}
