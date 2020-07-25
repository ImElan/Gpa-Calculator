package gpa.mit.india;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;

public class CumulativeGpaActivity extends AppCompatActivity implements CustomCgpaDialog.CustomDialogListener
{
    private RecyclerView mRecycler;
    public static int numberOfSemester;
    public static HashMap<Integer,Double> credits;
    public static HashMap<Integer,Double> gpa;
    private double numerator;
    private double denominator;
    private boolean display;
    public static HashMap<Integer,String> CreditSpinnerValue;
    public static HashMap<Integer,String> GpaSpinnerValue;
    private EditTextAdapter mAdapter;
    Spinner spinner;
    private double cgpa;
    DatabaseHelperCgpa Students_database;

    public static int count_check;

    public static int Gpa_Count;
    public static int cGpa_Count;


    private HashMap<Integer,String> ReceivingGPAMap;
    private HashMap<Integer,String> ReceivingCreditsMap;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumulative_gpa);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("CGPA Calculation");


        spinner = findViewById(R.id.spinner_semesters);
        credits = new HashMap<>();
        gpa = new HashMap<>();
        GpaSpinnerValue = new HashMap<>();
        CreditSpinnerValue = new HashMap<>();
        Students_database = new DatabaseHelperCgpa(this);


        ReceivingGPAMap = new HashMap<>();
        ReceivingCreditsMap = new HashMap<>();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.semesters, R.layout.spinner_custom_cgpa_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        mRecycler = findViewById(R.id.edit_text_recycler_list);
        mRecycler.setHasFixedSize(true);
        mRecycler.setItemViewCacheSize(numberOfSemester);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        /*Log.d("RECEIVING_CHECK","IN CGPA ACTIVITY Checking credit spinner gpa spinner all those things");
        Log.d("RECEIVING_CHECK","Credits List in GPA Activity size:"+MyGpaActivity.CreditsListToSend.size());
        Log.d("RECEIVING_CHECK","Gpa List in GPA Activity size:"+MyGpaActivity.GpaListToSend.size());
        Log.d("RECEIVING_CHECK","Credits List in CGPA Activity size:"+ViewMyCgpaActivity.CreditsListToSend.size());
        Log.d("RECEIVING_CHECK","Cgpa List in CGPA Activity size:"+ViewMyCgpaActivity.GpaListToSend.size());

        for(int i=0;i<MyGpaActivity.CreditsListToSend.size();i++)
        {
            Log.d("RECEIVING_CHECK","Credits List values(GPA ACTIVITY)"+MyGpaActivity.CreditsListToSend.get(i));
            Log.d("RECEIVING_CHECK","Gpa List values(GPA ACTIVITY)"+MyGpaActivity.GpaListToSend.get(i));
        }

        for(int i=0;i<ViewMyCgpaActivity.CreditsListToSend.size();i++)
        {
            Log.d("RECEIVING_CHECK","Credits List values(CGPA ACTIVITY)"+ViewMyCgpaActivity.CreditsListToSend.get(i));
            Log.d("RECEIVING_CHECK","Gpa List values(CGPA ACTIVITY)"+ViewMyCgpaActivity.GpaListToSend.get(i));
        }*/

        if(MyGpaActivity.GpaListToSend != null && ViewMyCgpaActivity.GpaListToSend != null)
        {
//            int check = ViewMyCgpaActivity.GpaListToSend.size()+MyGpaActivity.GpaListToSend.size();

            for(int i=0;i<ViewMyCgpaActivity.GpaListToSend.size();i++)
            {

                //*Log.d("RECEIVING_CHECK","IN CGPA ACTIVITY Checking whether the values are received");
                Log.d("RECEIVING_CHECK","First For loop... CGPA Gpalisttosend size:"+ViewMyCgpaActivity.GpaListToSend.size());
                Log.d("RECEIVING_CHECK","Gpa's: "+ViewMyCgpaActivity.GpaListToSend.get(i));//*

                ReceivingGPAMap.put(i,ViewMyCgpaActivity.GpaListToSend.get(i));
                ReceivingCreditsMap.put(i,ViewMyCgpaActivity.CreditsListToSend.get(i));
                Log.d("RECEIVING_CHECK", "Index: "+i+"GPA: "+ReceivingGPAMap.get(i)+"Credits: "+ReceivingCreditsMap.get(i));


                //*CreditSpinnerValue.put(i,ViewMyCgpaActivity.CreditsListToSend.get(i));
//                GpaSpinnerValue.put(i,ViewMyCgpaActivity.GpaListToSend.get(i));
                /*credits.put(i,Double.parseDouble(ViewMyCgpaActivity.CreditsListToSend.get(i)));
                gpa.put(i,Double.parseDouble(ViewMyCgpaActivity.GpaListToSend.get(i)));//**/
            }
            for(int i=0;i<MyGpaActivity.GpaListToSend.size();i++)
            {
                Log.d("RECEIVING_CHECK","Second For loop.....");

                Log.d("SECOND_FOR_LOOP","Size: "+MyGpaActivity.GpaListToSend.size()+"GPA: "+MyGpaActivity.GpaListToSend.get(i)+"Credits: "+MyGpaActivity.CreditsListToSend.get(i));

                ReceivingGPAMap.put(i+ViewMyCgpaActivity.GpaListToSend.size(),MyGpaActivity.GpaListToSend.get(i));
                ReceivingCreditsMap.put(i+ViewMyCgpaActivity.GpaListToSend.size(),MyGpaActivity.CreditsListToSend.get(i));
                Log.d("RECEIVING_CHECK", "Index: "+(i+ViewMyCgpaActivity.GpaListToSend.size())+" GPA: "+ReceivingGPAMap.get(i+ViewMyCgpaActivity.GpaListToSend.size())+" Credits: "+ReceivingCreditsMap.get(i+ViewMyCgpaActivity.GpaListToSend.size()));


                //*CreditSpinnerValue.put(i+ViewMyCgpaActivity.GpaListToSend.size(),MyGpaActivity.CreditsListToSend.get(i));
//                GpaSpinnerValue.put(i+ViewMyCgpaActivity.GpaListToSend.size(),MyGpaActivity.GpaListToSend.get(i));
                /*credits.put(i+ViewMyCgpaActivity.GpaListToSend.size()-1,Double.parseDouble(MyGpaActivity.CreditsListToSend.get(i)));
                gpa.put(i+ViewMyCgpaActivity.GpaListToSend.size()-1,Double.parseDouble(MyGpaActivity.GpaListToSend.get(i)));//**/
            }

            for(int i=0;i<ReceivingCreditsMap.size();i++)
            {
                CreditSpinnerValue.put(i,ReceivingCreditsMap.get(i));
                GpaSpinnerValue.put(i,ReceivingGPAMap.get(i));
                Log.d("RECEIVING_CHECK", "___SPINNER VALUE___ Index: "+i+"GPA: "+CreditSpinnerValue.get(i)+"Credits: "+GpaSpinnerValue.get(i));
                credits.put(i,Double.parseDouble(ReceivingCreditsMap.get(i)));
                gpa.put(i,Double.parseDouble(ReceivingGPAMap.get(i)));
                Log.d("RECEIVING_CHECK", "___GPA CREDITS___ Index: "+i+"GPA: "+credits.get(i)+"Credits: "+gpa.get(i));
            }

            for(int i=ReceivingGPAMap.size();i<8;i++)
            {
                CreditSpinnerValue.put(i,"");
                GpaSpinnerValue.put(i,"");
                Log.d("RECEIVING_CHECK", "___SPINNER VALUE___ Index: "+i+"GPA: "+CreditSpinnerValue.get(i)+"Credits: "+GpaSpinnerValue.get(i));
                credits.put(i,null);
                gpa.put(i,null);
                Log.d("RECEIVING_CHECK", "___GPA CREDITS___ Index: "+i+"GPA: "+credits.get(i)+"Credits: "+gpa.get(i));
            }
            int ValueToBeSetOnSpinner = MyGpaActivity.GpaListToSend.size()+ViewMyCgpaActivity.GpaListToSend.size();
            spinner.setSelection(ValueToBeSetOnSpinner);
        }


        else if(MyGpaActivity.GpaListToSend!=null && ViewMyCgpaActivity.GpaListToSend == null)
        {
            int ValueToBeSetOnSpinner = MyGpaActivity.GpaListToSend.size();
            spinner.setSelection(ValueToBeSetOnSpinner);
//            Log.d("ADD_TO_GPA_CALCULATION","Spinner Value:"+ValueToBeSetOnSpinner);
            for(int i=0;i<MyGpaActivity.GpaListToSend.size();i++)
            {
            /*CreditSpinnerValue.put(i,"");
            GpaSpinnerValue.put(i,"");*/
                CreditSpinnerValue.put(i,MyGpaActivity.CreditsListToSend.get(i));
                GpaSpinnerValue.put(i,MyGpaActivity.GpaListToSend.get(i));

                credits.put(i,Double.parseDouble(MyGpaActivity.CreditsListToSend.get(i)));
                gpa.put(i,Double.parseDouble(MyGpaActivity.GpaListToSend.get(i)));

                //*mAdapter = new EditTextAdapter(ValueToBeSetOnSpinner,CreditSpinnerValue,GpaSpinnerValue);
                mRecycler.setAdapter(mAdapter);//*

                //*Log.d("ADD_TO_GPA_CALCULATION","Credits received from MyGpa"+CreditSpinnerValue.get(i)+"" +
//                        " Grades Received from My Gpa"+GpaSpinnerValue.get(i);
                Log.d("ADD_TO_GPA_CALCULATION", "Size of List: "+MyGpaActivity.GpaListToSend.size());//*
            }
            for(int k=MyGpaActivity.GpaListToSend.size();k<8;k++)
            {
                Log.d("ADD_TO_GPA_CALCULATION", "k: "+k);
                CreditSpinnerValue.put(k,"");
                GpaSpinnerValue.put(k,"");
                credits.put(k,null);
                gpa.put(k,null);
//                Log.d("ADD_TO_GPA_CALCULATION","Remaining Values Set:"+CreditSpinnerValue.get(k)+" : "+GpaSpinnerValue.get(k));
            }
        }
        else if(ViewMyCgpaActivity.GpaListToSend!=null && MyGpaActivity.GpaListToSend == null)
        {
            int ValueToBeSetOnSpinner = ViewMyCgpaActivity.GpaListToSend.size();
            spinner.setSelection(ValueToBeSetOnSpinner);
//            Log.d("ADD_TO_GPA_CALCULATION","Spinner Value:"+ValueToBeSetOnSpinner);
            for(int i=0;i<ViewMyCgpaActivity.GpaListToSend.size();i++)
            {
            /*//*CreditSpinnerValue.put(i,"");
            GpaSpinnerValue.put(i,"");//**/
                CreditSpinnerValue.put(i,ViewMyCgpaActivity.CreditsListToSend.get(i));
                GpaSpinnerValue.put(i,ViewMyCgpaActivity.GpaListToSend.get(i));

                credits.put(i,Double.parseDouble(ViewMyCgpaActivity.CreditsListToSend.get(i)));
                gpa.put(i,Double.parseDouble(ViewMyCgpaActivity.GpaListToSend.get(i)));

                //*mAdapter = new EditTextAdapter(ValueToBeSetOnSpinner,CreditSpinnerValue,GpaSpinnerValue);
                mRecycler.setAdapter(mAdapter);//*

                //*Log.d("ADD_TO_GPA_CALCULATION","Credits received from MyGpa"+CreditSpinnerValue.get(i)+"" +
//                        " Grades Received from My Gpa"+GpaSpinnerValue.get(i));
//                Log.d("ADD_TO_GPA_CALCULATION", "Size of List: "+MyGpaActivity.GpaListToSend.size());//*
            }
            for(int k=ViewMyCgpaActivity.GpaListToSend.size();k<8;k++)
            {
                Log.d("ADD_TO_GPA_CALCULATION", "k: "+k);
                CreditSpinnerValue.put(k,"");
                GpaSpinnerValue.put(k,"");
                credits.put(k,null);
                gpa.put(k,null);
//                Log.d("ADD_TO_GPA_CALCULATION","Remaining Values Set:"+CreditSpinnerValue.get(k)+" : "+GpaSpinnerValue.get(k));
            }
        }
        else
        {
            for(int i=0;i<8;i++)
            {
                CreditSpinnerValue.put(i,"");
                GpaSpinnerValue.put(i,"");
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                /*for(int i=0;i<8;i++)
                {
                    Log.d("SWIPE_TO_DELETE","Index:"+i+" CreditSpinner value:"+CreditSpinnerValue.get(i)+"GpaSpinner value:"+GpaSpinnerValue.get(i));
                }*/
                numberOfSemester = Integer.parseInt(adapterView.getItemAtPosition(position).toString());
                mAdapter = new EditTextAdapter(numberOfSemester,CreditSpinnerValue,GpaSpinnerValue);
                mRecycler.setAdapter(mAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecycler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        Log.d("ERROR_CHECK_FINAL","Before Calculate Button ..Credits:"+credits.get(0)+" Gpa:"+gpa.get(0));
        Button Calculate_button = findViewById(R.id.calculate_id);
        Calculate_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                Log.d("ERROR_CHECK_FINAL","Number Of Semesters:"+numberOfSemester);
                if(numberOfSemester != 0)
                {
                    numerator = 0;
                    denominator = 0;
                    for(int i=0;i<numberOfSemester;i++)
                    {
                        Log.d("ERROR_CHECK_FINAL","Index:"+i+" Credits:"+credits.get(i)+" Gpa:"+gpa.get(i));
                        if(credits.get(i) == null || gpa.get(i) == null)
                        {
                            Toast.makeText(CumulativeGpaActivity.this,"Please Enter GPA and Credits",Toast.LENGTH_LONG).show();
                            display=false;
                            break;
                        }
                        else
                        {
                            numerator = numerator+ credits.get(i) * gpa.get(i);
                            denominator = denominator + credits.get(i);
                        }
                        display=true;
                        /*Log.d("GPA_LOG","Testing:"+credits[1]);*/
                    }
                    Log.d("GPA_LOG","Numerator:"+numerator);
                    Log.d("GPA_LOG","Denominator:"+denominator);
                    cgpa = numerator/denominator;
                    DecimalFormat precision = new DecimalFormat("0.000");
                    cgpa = Double.parseDouble(precision.format(cgpa));
                    Log.d("GPA_LOG","CGPA:"+cgpa);
                    /*if (Double.toString(cgpa).equals("NaN"))
                    {
                        Toast.makeText(CumulativeGpaActivity.this,"Please Enter GPA and Credits",Toast.LENGTH_LONG).show();
                    }*/
                    if(display)
                    {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(CumulativeGpaActivity.this);
                        builder.setTitle("CGPA Calculation")
                                .setMessage("Your CGPA is "+cgpa)
                                .setPositiveButton("Close",null)
                                .setNegativeButton("Save and Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        CustomCgpaDialog customCgpaDialog = new CustomCgpaDialog();
                                        customCgpaDialog.show(getSupportFragmentManager(),"CUSTOM_DIALOG");
                                        customCgpaDialog.setCancelable(false);
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    }
                    else
                    {
                        Toast.makeText(CumulativeGpaActivity.this,"Please Enter GPA and Credits",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(CumulativeGpaActivity.this,"Please Select the Number Of Semesters",Toast.LENGTH_LONG).show();
                }
            }
        });

        LinearLayout layout = findViewById(R.id.add_gpa_tab_id);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOneMore();
            }
        });

        ImageButton addButton = findViewById(R.id.add_gap_tab_button_id);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOneMore();
            }
        });

    }
    private void AddOneMore()
    {
        if(numberOfSemester < 8)
        {
            int current_value = Integer.parseInt(spinner.getSelectedItem().toString());
            spinner.setSelection(current_value+1);
            mAdapter = new EditTextAdapter(current_value+1,CreditSpinnerValue,GpaSpinnerValue);
            mRecycler.setAdapter(mAdapter);
//            Log.d("SUBJECT_TAG","Number of subjects After Adding:"+numberOfSemester);
        }
        else
        {
            Toast.makeText(CumulativeGpaActivity.this,"Maximum number of Semester is reached",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CumulativeGpaActivity.this);
        builder.setTitle("Go back to main page")
                .setMessage("Are You Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyGpaActivity.GpaListToSend = null;
                        MyGpaActivity.CreditsListToSend = null;
                        ViewMyCgpaActivity.CreditsListToSend = null;
                        ViewMyCgpaActivity.GpaListToSend = null;
                        count_check = 0;
                        cGpa_Count = 0;
                        Gpa_Count = 0;
                        CumulativeGpaActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @SuppressWarnings({"StringBufferMayBeStringBuilder", "StringConcatenationInsideStringBufferAppend"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            MyGpaActivity.GpaListToSend = null;
            ViewMyCgpaActivity.GpaListToSend = null;
            MyGpaActivity.CreditsListToSend = null;
            ViewMyCgpaActivity.CreditsListToSend = null;
            count_check = 0;
            cGpa_Count = 0;
            Gpa_Count = 0;
            super.onBackPressed();
        }
        if(id == R.id.view_cgpa_id)
        {
            Cursor data = Students_database.ViewData();
            if(data.getCount() == 0)
            {
                DisplayMethodCGPA("You haven't stored any CGPAs");
                return super.onOptionsItemSelected(item);
            }
            StringBuffer buffer = new StringBuffer();
            while(data.moveToNext())
            {
                buffer.append("Name : " + data.getString(0) + "\n");
                buffer.append("Credits : " + data.getString(1) + "\n");
                buffer.append("CGPA : " + data.getString(2) + "\n");
                buffer.append("\n");
            }
            DisplayMethodCGPA(buffer.toString());
        }
        if(id == R.id.view_gpa_id)
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Cursor data = databaseHelper.ViewData();
            if(data.getCount() == 0)
            {
                DisplayMethod("You haven't stored any GPAs");
                return super.onOptionsItemSelected(item);
            }
            StringBuffer buffer = new StringBuffer();
            while(data.moveToNext())
            {
                buffer.append("Name : " + data.getString(0) + "\n");
                buffer.append("Credits : " + data.getString(1) + "\n");
                buffer.append("CGPA : " + data.getString(2) + "\n");
                buffer.append("\n");
            }
            DisplayMethod(buffer.toString());
        }

        return super.onOptionsItemSelected(item);
    }

    private void DisplayMethod(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("MY GPAs")
                .setMessage(message)
                .setPositiveButton("Close",null)
                .show();
    }
    private void DisplayMethodCGPA(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("My CGPAs")
                .setMessage(message)
                .setPositiveButton("Close",null)
                .show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
        {
            Log.d("SWIPE_TO_DELETE","Swiping calls This");
            /*CreditSpinnerValue.remove(viewHolder.getAdapterPosition());
            GpaSpinnerValue.remove(viewHolder.getAdapterPosition());
            gpa.remove(viewHolder.getAdapterPosition());
            credits.remove(viewHolder.getAdapterPosition());*/
            for(int i=viewHolder.getAdapterPosition();i<numberOfSemester-1;i++)
            {
                gpa.put(i,gpa.get(i+1));
                credits.put(i,credits.get(i+1));
                CreditSpinnerValue.put(i,CreditSpinnerValue.get(i+1));
                GpaSpinnerValue.put(i,GpaSpinnerValue.get(i+1));
            }
            for(int k=numberOfSemester-1;k<8;k++)
            {
                gpa.put(k,null);
                credits.put(k,null);
                CreditSpinnerValue.put(k,"");
                GpaSpinnerValue.put(k,"");
            }
//            for(int i=0;i<8;i++)
//            {
//                Log.d("SWIPE_TO_DELETE","Index:"+i+" CreditSpinner value:"+CreditSpinnerValue.get(i)+"GpaSpinner value:"+GpaSpinnerValue.get(i));
//            }
            int current_value = Integer.parseInt(spinner.getSelectedItem().toString());
            spinner.setSelection(current_value-1);
            mAdapter = new EditTextAdapter(current_value-1,CreditSpinnerValue,GpaSpinnerValue);
            mRecycler.setAdapter(mAdapter);
//            Log.d("SUBJECT_TAG","Number of subjects After swiped one entry(deleted):"+numberOfSubjects);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.about_menu,menu);
        return true;
    }


    @Override
    public void ApplyText(String name_tag) {
        if(!TextUtils.isEmpty(name_tag))
        {
            boolean insertData = Students_database.AddData(name_tag,Double.toString(denominator),Double.toString(cgpa));
            if(insertData)
                Toast.makeText(CumulativeGpaActivity.this,"CGPA Added Successfully",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(CumulativeGpaActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomCgpaDialog customCgpaDialog = new CustomCgpaDialog();
            customCgpaDialog.show(getSupportFragmentManager(),"CUSTOM_DIALOG");
            customCgpaDialog.setCancelable(false);
            Toast.makeText(CumulativeGpaActivity.this,"Field Should not be empty..!",Toast.LENGTH_SHORT).show();
        }
    }
}
