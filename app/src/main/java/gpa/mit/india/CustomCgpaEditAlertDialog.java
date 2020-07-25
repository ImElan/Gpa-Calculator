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

public class CustomCgpaEditAlertDialog extends AppCompatDialogFragment
{
    private TextInputLayout Name_tag;
    private TextInputLayout Credits;
    private TextInputLayout Gpa;
    private CustomCgpaEditAlertDialog.CustomCgpaEditDialogListener listener;
    private String name_id;
    private String credits_id;
    private String gpa_id;
    DatabaseHelperCgpa StudentDatabase;
    private String name_tag_previous;

    public CustomCgpaEditAlertDialog(String name,String credits,String gpa)
    {
        name_id = name;
        credits_id = credits;
        gpa_id = gpa;
        name_tag_previous = name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        StudentDatabase = new DatabaseHelperCgpa(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_edit_alert_dialog,null);

        Name_tag = view.findViewById(R.id.name_tag_id);
        Credits = view.findViewById(R.id.edit_credits);
        Gpa = view.findViewById(R.id.edit_gpa);

        Name_tag.getEditText().setText(name_id);
        Credits.getEditText().setText(credits_id);
        Gpa.getEditText().setText(gpa_id);


        builder.setView(view)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name_tag = Name_tag.getEditText().getText().toString();
                        String credits_value = Credits.getEditText().getText().toString();
                        String gpa_value = Gpa.getEditText().getText().toString();
                        if(name_tag.equals(name_id))
                            listener.ApplyEditDataText(name_id,name_tag,credits_value,gpa_value);
                        else if(!StudentDatabase.checkAlreadyExist(name_tag))
                            listener.ApplyEditDataText(name_id,name_tag,credits_value,gpa_value);
                        else
                        {
                            CustomCgpaEditAlertDialog customEditAlertDialog = new CustomCgpaEditAlertDialog(name_tag_previous,credits_value,gpa_value);
                            customEditAlertDialog.show(getActivity().getSupportFragmentManager(),"CUSTOM_EDIT_DIALOG");
                            customEditAlertDialog.setCancelable(false);
                            Toast.makeText(getContext(),"Name Already exist.Try different name",Toast.LENGTH_SHORT).show();
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
            listener = (CustomCgpaEditAlertDialog.CustomCgpaEditDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "Error:Must implement Custom Dialog");
        }
    }

    public interface CustomCgpaEditDialogListener
    {
        void ApplyEditDataText(String name_id,String name_tag,String credits,String gpa);
    }
}
