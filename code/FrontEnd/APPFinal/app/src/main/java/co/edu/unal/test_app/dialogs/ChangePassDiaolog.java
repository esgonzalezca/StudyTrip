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

public class ChangePassDiaolog extends AppCompatDialogFragment {

    private EditText editLastPass;
    private EditText editNewPass;
    private ChangePassDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialog_password,null);
        builder.setView(view).setTitle("Cambiar Clave")
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                    }
                })
                .setPositiveButton("cambiar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface , int i){
                listener.sentDataForPass(editLastPass.getText().toString(),editNewPass.getText().toString()); ;
            }
        });

        editLastPass=view.findViewById(R.id.last_password);
        editNewPass=view.findViewById(R.id.new_password);
    return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ChangePassDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Listener");
        }
    }

    public interface ChangePassDialogListener{
        void sentDataForPass(String lastPass, String newPass);
    }
}
