package co.edu.unal.test_app.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDialogFragment;
import co.edu.unal.test_app.R;

public class UtilDialog extends AppCompatDialogFragment {

    private EditText editPass;
    private UtilDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.dialog_ip,null);
        builder.setView(view).setTitle("¿Desea Cambiar la IP del servidor?")
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                    }
                })
                .setPositiveButton("Cambiar",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                        listener.sentDataForPass(editPass.getText().toString()); ;
                    }
                });

        editPass=view.findViewById(R.id.IPText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (UtilDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Listener");
        }
    }

    public interface UtilDialogListener{
        void sentDataForPass(String pass);
    }
}
