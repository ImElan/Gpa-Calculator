package gpa.mit.india;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;

public class CustomAddAlertDialog extends AppCompatDialogFragment
{
    private TextInputLayout Name_tag;
    private TextInputLayout Credits;
    private TextInputLayout Gpa;
    private CustomAddAlertDialog.CustomAddAlertDialogListener listener;
    DatabaseHelper StudentDatabase;

    public CustomAddAlertDialog()
    {

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        StudentDatabase = new DatabaseHelper(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_add_alert_dialog_layout,null);

        Name_tag = view.findViewById(R.id.name_tag_id);
        Credits = view.findViewById(R.id.edit_credits);
        Gpa = view.findViewById(R.id.edit_gpa);


        builder.setView(view)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Add", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name_tag = Name_tag.getEditText().getText().toString();
                        String credits_value = Credits.getEditText().getText().toString();
                        String gpa_value = Gpa.getEditText().getText().toString();

                        if(!StudentDatabase.checkAlreadyExist(name_tag))
                            listener.ApplyAddDataText(name_tag,credits_value,gpa_value);
                        else
                        {
                            CustomAddAlertDialog customAddAlertDialog = new CustomAddAlertDialog();
                            customAddAlertDialog.show(getActivity().getSupportFragmentManager(),"CUSTOM_EDIT_DIALOG");
                            customAddAlertDialog.setCancelable(false);
                            Toast.makeText(getActivity(),"Name Already exist.Try different name",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try
        {
            listener = (CustomAddAlertDialog.CustomAddAlertDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "Error:Must implement Custom Dialog");
        }
    }

    public interface CustomAddAlertDialogListener
    {
        void ApplyAddDataText(String name_tag,String credits,String gpa);
    }
}
