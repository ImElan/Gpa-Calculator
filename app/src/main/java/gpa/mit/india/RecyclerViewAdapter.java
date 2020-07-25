package gpa.mit.india;

import android.annotation.SuppressLint;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    private ArrayList<String> nameList;
    private ArrayList<String> creditsList;
    private ArrayList<String> gpaList;
    private Context mContext;
    private DatabaseHelper StudentDatabase;
    private int count;


    public RecyclerViewAdapter(Context mContext,ArrayList<String> nameList, ArrayList<String> creditsList, ArrayList<String> gpaList,int count)
    {
        this.nameList = nameList;
        this.creditsList = creditsList;
        this.gpaList = gpaList;
        this.mContext = mContext;
        this.count = count;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_result_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {

        holder.serial_no.setText(position+1+".");
        holder.name_tag.setText(nameList.get(position));
        holder.credits.setText(creditsList.get(position));
        holder.gpa.setText(gpaList.get(position));

        holder.editButton.setOnClickListener(new View.OnClickListener()
        {
            String name_tag = nameList.get(position);
            String credits_tag = creditsList.get(position);
            String gpa_tag = gpaList.get(position);
            @Override
            public void onClick(View view)
            {
                CustomEditAlertDialog customEditAlertDialog = new CustomEditAlertDialog(name_tag,credits_tag,gpa_tag);
                customEditAlertDialog.show(((MyGpaActivity)mContext).getSupportFragmentManager(),"CUSTOM_EDIT_DIALOG");
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
                        .setMessage("Are you Sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                StudentDatabase = new DatabaseHelper(mContext);
                                int result = StudentDatabase.DeletaData(name_id);
                                if(result==1)
                                {
                                    nameList.remove(position);
                                    creditsList.remove(position);
                                    gpaList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,nameList.size());
                                    Toast.makeText(mContext,"GPA Deleted Successfully",Toast.LENGTH_SHORT).show();
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
                    String Gpa = holder.gpa.getText().toString();
//                    Log.d("ADD_TO_GPA_CALCULATION","Count : "+count);
                    Log.d("RECEIVING_CHECK","Check Count : "+CumulativeGpaActivity.count_check);
                    Log.d("RECEIVING_CHECK","Gpa Count : "+CumulativeGpaActivity.Gpa_Count);
                    MyGpaActivity.CreditsListToSend.put(CumulativeGpaActivity.Gpa_Count,Credits);
                    MyGpaActivity.GpaListToSend.put(CumulativeGpaActivity.Gpa_Count,Gpa);
                    Toast.makeText(mContext,"Gpa added",Toast.LENGTH_SHORT).show();
                    Log.d("RECEIVING_CHECK","__IN GPA__ Index:"+CumulativeGpaActivity.Gpa_Count+"CreditsList: "+MyGpaActivity.CreditsListToSend.get(CumulativeGpaActivity.Gpa_Count)
                            +"GPA List: "+MyGpaActivity.GpaListToSend.get(CumulativeGpaActivity.Gpa_Count));
                    CumulativeGpaActivity.Gpa_Count++;
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
    public int getItemCount()
    {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView serial_no;
        TextView name_tag;
        TextView credits;
        TextView gpa;
        TextView editButton;
        TextView deleteButton;
        TextView addButton;

        ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            serial_no = itemView.findViewById(R.id.count_id);
            name_tag = itemView.findViewById(R.id.name_tag);
            credits = itemView.findViewById(R.id.credits);
            gpa = itemView.findViewById(R.id.gpa);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.remove_button);
            addButton = itemView.findViewById(R.id.add_button);
        }
    }
}
