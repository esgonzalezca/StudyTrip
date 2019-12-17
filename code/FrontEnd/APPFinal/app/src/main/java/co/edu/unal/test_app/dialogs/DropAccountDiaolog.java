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

public class DropAccountDiaolog extends AppCompatDialogFragment {

    private EditText editPass;
    private DropAccountDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater= getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.layout_dialog_dropaccount,null);
        builder.setView(view).setTitle("¿Desea eliminar su cuenta?")
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface , int i){
                    }
                })
                .setPositiveButton("Eliminar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface , int i){
                listener.sentDataForPass(editPass.getText().toString()); ;
            }
        });

        editPass=view.findViewById(R.id.password);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DropAccountDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement Listener");
        }
    }

    public interface DropAccountDialogListener{
        void sentDataForPass(String pass);
    }
}
