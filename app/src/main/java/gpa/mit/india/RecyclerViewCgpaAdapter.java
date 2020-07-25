package gpa.mit.india;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewCgpaAdapter extends RecyclerView.Adapter<RecyclerViewCgpaAdapter.CGPAViewHolder>
{
    private ArrayList<String> nameList;
    private ArrayList<String> creditsList;
    private ArrayList<String> gpaList;
    private Context mContext;
    private DatabaseHelperCgpa StudentDatabase;
    private int count;


    public RecyclerViewCgpaAdapter(Context mContext, ArrayList<String> nameList, ArrayList<String> creditsList, ArrayList<String> gpaList, int count)
    {
        this.nameList = nameList;
        this.creditsList = creditsList;
        this.gpaList = gpaList;
        this.mContext = mContext;
        this.count = count;
    }

    @NonNull
    @Override
    public CGPAViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_result_cgpa_layout,parent,false);
        CGPAViewHolder viewHolder = new CGPAViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CGPAViewHolder holder, final int position)
    {
        holder.serial_no.setText(position+1+".");
        holder.name_tag.setText(nameList.get(position));
        holder.credits.setText(creditsList.get(position));
        holder.cgpa.setText(gpaList.get(position));

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            String name_tag = nameList.get(position);
            String credits_tag = creditsList.get(position);
            String gpa_tag = gpaList.get(position);

            @Override
            public void onClick(View view)
            {
                CustomCgpaEditAlertDialog customEditAlertDialog = new CustomCgpaEditAlertDialog(name_tag,credits_tag,gpa_tag);
                customEditAlertDialog.show(((ViewMyCgpaActivity)mContext).getSupportFragmentManager(),"CUSTOM_EDIT_DIALOG");
                customEditAlertDialog.setCancelable(false);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            String name_id = nameList.get(position);
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete")
                        .setMessage("Are you Sure")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StudentDatabase = new DatabaseHelperCgpa(mContext);
                                int result = StudentDatabase.DeletaData(name_id);
                                if(result==1)
                                {
                                    nameList.remove(position);
                                    creditsList.remove(position);
                                    gpaList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,nameList.size());
                                    Toast.makeText(mContext,"CGPA Deleted Successfully",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No",null);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(CumulativeGpaActivity.count_check<8)
                {
                    String Credits = holder.credits.getText().toString();
                    String Gpa = holder.cgpa.getText().toString();
//                    Log.d("ADD_TO_GPA_CALCULATION","Count : "+count);
                    Log.d("RECEIVING_CHECK","Check Count : "+CumulativeGpaActivity.count_check);
                    Log.d("RECEIVING_CHECK","CGpa Count : "+CumulativeGpaActivity.cGpa_Count);
                    ViewMyCgpaActivity.CreditsListToSend.put(CumulativeGpaActivity.cGpa_Count,Credits);
                    ViewMyCgpaActivity.GpaListToSend.put(CumulativeGpaActivity.cGpa_Count,Gpa);
                    Toast.makeText(mContext,"Gpa added",Toast.LENGTH_SHORT).show();
                    Log.d("RECEIVING_CHECK","__IN GPA__ Index: "+CumulativeGpaActivity.cGpa_Count+"CreditsList: "+ViewMyCgpaActivity.CreditsListToSend.get(CumulativeGpaActivity.cGpa_Count)
                            +"GPA List: "+ViewMyCgpaActivity.GpaListToSend.get(CumulativeGpaActivity.cGpa_Count));
                    CumulativeGpaActivity.cGpa_Count++;
                    CumulativeGpaActivity.count_check++;
                }
                else
                {
                    Toast.makeText(mContext,"Only 8 Semester's GPA/CGPA can be included to calculate CGPA",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class CGPAViewHolder extends RecyclerView.ViewHolder
    {
        TextView serial_no;
        TextView name_tag;
        TextView credits;
        TextView cgpa;
        TextView editButton;
        TextView deleteButton;
        TextView addButton;

        public CGPAViewHolder(@NonNull View itemView)
        {
            super(itemView);

            serial_no = itemView.findViewById(R.id.count_id);
            name_tag = itemView.findViewById(R.id.name_tag);
            credits = itemView.findViewById(R.id.credits);
            cgpa = itemView.findViewById(R.id.cgpa);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.remove_button);
            addButton = itemView.findViewById(R.id.add_button);
        }
    }
}
