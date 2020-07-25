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

public class CustomCgpaDialog extends AppCompatDialogFragment
{
    private TextInputLayout Name_tag;
    private CustomCgpaDialog.CustomDialogListener listener;
    DatabaseHelperCgpa StudentDatabase;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        StudentDatabase = new DatabaseHelperCgpa(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_alert_dialog_cgpa_layout,null);

        Name_tag = view.findViewById(R.id.name_tag_id);

        builder.setView(view)
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        String name_tag = Name_tag.getEditText().getText().toString();
                        if(!StudentDatabase.checkAlreadyExist(name_tag))
                            listener.ApplyText(name_tag);
                        else
                        {
                            CustomCgpaDialog customCgpaDialog = new CustomCgpaDialog();
                            customCgpaDialog.show(getFragmentManager(),"CUSTOM_DIALOG");
                            customCgpaDialog.setCancelable(false);
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
            listener = (CustomCgpaDialog.CustomDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "Error:Must implement Custom Dialog");
        }
    }

    public interface CustomDialogListener
    {
        void ApplyText(String name);
    }
}
