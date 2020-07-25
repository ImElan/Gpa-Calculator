package gpa.mit.india;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class EditTextAdapter extends RecyclerView.Adapter<EditTextAdapter.EditViewHolder>
{
    private int numberOfSem;
    private HashMap<Integer,String> CreditSpinnerValue;
    private HashMap<Integer,String> GpaSpinnerValue;

    EditTextAdapter(int numberOfSem,HashMap<Integer,String> CreditSpinnerValue,HashMap<Integer,String> GpaSpinnerValue)
    {
        this.numberOfSem = numberOfSem;
        this.CreditSpinnerValue = CreditSpinnerValue;
        this.GpaSpinnerValue = GpaSpinnerValue;
    }


    @NonNull
    @Override
    public EditTextAdapter.EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cgpa_semester_layout,parent,false);
        return new EditTextAdapter.EditViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final EditTextAdapter.EditViewHolder holder, final int position)
    {
        holder.Credits_text_input_layout.getEditText().setText(CreditSpinnerValue.get(position));
        holder.Gpa_text_input_layout.getEditText().setText(GpaSpinnerValue.get(position));
        holder.Credits_text_input_layout.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String credits = holder.Credits_text_input_layout.getEditText().getText().toString();
                if(!(TextUtils.isEmpty(credits)))
                {
                    CreditSpinnerValue.put(position,credits);
                    CumulativeGpaActivity.credits.put(position,Double.parseDouble(credits));
                }
                else
                {
                    if(CumulativeGpaActivity.credits.get(position)!=null)
                    {
                        CumulativeGpaActivity.credits.put(position,null);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        holder.Gpa_text_input_layout.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String gpa = holder.Gpa_text_input_layout.getEditText().getText().toString();
//                Log.d("INSIDE_RECYCLER",".....Position:"+position+"...Credits:"+gpa);
                if(!(TextUtils.isEmpty(gpa)))
                {
                    GpaSpinnerValue.put(position, gpa);
                    CumulativeGpaActivity.gpa.put(position, Double.parseDouble(gpa));
//                    Log.d("LOG_TAG", +position + "Gpa:" + CumulativeGpaActivity.gpa.get(position));
                }
                else
                {
                    if(CumulativeGpaActivity.gpa.get(position)!=null)
                    {
                        CumulativeGpaActivity.gpa.put(position,null);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        holder.serial_text.setText(Integer.toString(position+1));
    }

    @Override
    public int getItemCount()
    {
        return numberOfSem;
    }

    /*@Override
    public void onViewRecycled(@NonNull EditViewHolder holder) {
        super.onViewRecycled(holder);
        CumulativeGpaActivity.gpa[holder.getAdapterPosition()] = Double.parseDouble(holder.Gpa_text_input_layout.getEditText().toString());
        CumulativeGpaActivity.credits[holder.getAdapterPosition()] = Double.parseDouble(holder.Credits_text_input_layout.getEditText().toString());
    }*/

    class EditViewHolder extends RecyclerView.ViewHolder
    {
        TextView serial_text;
        TextInputLayout Gpa_text_input_layout;
        TextInputLayout Credits_text_input_layout;
        EditViewHolder(@NonNull View itemView)
        {
            super(itemView);
            serial_text = itemView.findViewById(R.id.serial_no_id);
            Gpa_text_input_layout = itemView.findViewById(R.id.gpa_id);
            Credits_text_input_layout = itemView.findViewById(R.id.credits_id);
        }
    }
}
