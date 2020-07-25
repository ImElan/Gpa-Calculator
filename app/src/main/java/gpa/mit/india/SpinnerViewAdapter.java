package gpa.mit.india;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class SpinnerViewAdapter extends RecyclerView.Adapter<SpinnerViewAdapter.SpinnerViewHolder>
{
    private ArrayList<ArrayAdapter<CharSequence>> creditsAdapter;
    private ArrayList<ArrayAdapter<CharSequence>> gradesAdapter;
    private HashMap<Integer,Integer> CreditSpinnerValue;
    private HashMap<Integer,Integer> GradeSpinnerValue;

    SpinnerViewAdapter(ArrayList<ArrayAdapter<CharSequence>> credits,ArrayList<ArrayAdapter<CharSequence>> grades,HashMap<Integer,Integer>
            creditSpinnerValue,HashMap<Integer,Integer> gradeSpinnerValue)
    {
        creditsAdapter = credits;
        gradesAdapter = grades;
        CreditSpinnerValue = creditSpinnerValue;
        GradeSpinnerValue = gradeSpinnerValue;
    }

    @NonNull
    @Override
    public SpinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_semester_layout,parent,false);
        return new SpinnerViewHolder(mView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SpinnerViewHolder holder,final int position_list)
    {
        final int position1 = position_list;
        holder.spinner_credit.setAdapter(creditsAdapter.get(position_list));
        holder.spinner_grade.setAdapter(gradesAdapter.get(position_list));


        holder.spinner_credit.setSelection(CreditSpinnerValue.get(position_list));
        holder.spinner_grade.setSelection(GradeSpinnerValue.get(position_list));

        holder.spinner_credit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                CreditSpinnerValue.put(position_list,position);
                int item = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
                GpaActivity.credits.put(position1,item);
                Log.d("LOG_TAG",+position1+":"+GpaActivity.credits.get(position1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                GradeSpinnerValue.put(position_list,position);
                String item = adapterView.getItemAtPosition(position).toString();
                GpaActivity.grades.put(position1,item);
                Log.d("LOG_TAG",+position1+":"+GpaActivity.grades.get(position1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.serial_text.setText(Integer.toString(position_list+1));
    }

    @Override
    public int getItemCount()
    {
        return creditsAdapter.size();
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder
    {
        Spinner spinner_credit;
        Spinner spinner_grade;
        TextView serial_text;
        SpinnerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            spinner_credit = itemView.findViewById(R.id.spinner_credit);
            spinner_grade = itemView.findViewById(R.id.spinner_grade);
            serial_text = itemView.findViewById(R.id.serial_no_id);
        }
    }
}
