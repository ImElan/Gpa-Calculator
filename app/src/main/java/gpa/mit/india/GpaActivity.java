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
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GpaActivity extends AppCompatActivity implements CustomDialog.CustomDialogListener
{
    private RecyclerView mRecycler;
    public static int numberOfSubjects;
    public static HashMap<Integer,Integer> credits;
    public static HashMap<Integer,String> grades;
    private HashMap<Integer,Integer> GradePoint;
    private int numerator;
    public int denominator;
    private Spinner spinner;
    private SpinnerViewAdapter mAdapter;
    public double GPA;
    private ArrayList<ArrayAdapter<CharSequence>> creditsAdapter;
    private ArrayList<ArrayAdapter<CharSequence>> gradesAdapter;
    private HashMap<Integer,Integer> CreditSpinnerValue;
    private HashMap<Integer,Integer> GradeSpinnerValue;
    DatabaseHelper Students_database;

    private ArrayAdapter<CharSequence> adapter_credits;
    private ArrayAdapter<CharSequence> adapter_grades;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("GPA Calculation");

        creditsAdapter = new ArrayList<>();
        gradesAdapter = new ArrayList<>();

        credits = new HashMap<>();
        grades = new HashMap<>();

        GradeSpinnerValue = new HashMap<>();
        CreditSpinnerValue = new HashMap<>();

        Students_database = new DatabaseHelper(this);

        spinner = findViewById(R.id.spinner_subjects);

        adapter_credits = ArrayAdapter.createFromResource(GpaActivity.this,
                R.array.credits, R.layout.spinner_custom_layout);
        adapter_credits.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter_grades = ArrayAdapter.createFromResource(GpaActivity.this,
                R.array.grades, R.layout.spinner_custom_layout);
        adapter_grades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.subjects, R.layout.spinner_custom_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mRecycler = findViewById(R.id.spinner_recycler_list);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        for(int i=0;i<15;i++)
        {
            CreditSpinnerValue.put(i,2);
            GradeSpinnerValue.put(i,2);
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                creditsAdapter.clear();
                gradesAdapter.clear();
                numberOfSubjects = Integer.parseInt(adapterView.getItemAtPosition(position).toString());

                for(int i=0;i<numberOfSubjects;i++)
                {
                    creditsAdapter.add(adapter_credits);
                    gradesAdapter.add(adapter_grades);
//                    Log.d("ADAPTER_TAG","CreditsAdapter Entry:"+i+" GradesAdapter Entry:"+i);
                }

                mAdapter = new SpinnerViewAdapter(creditsAdapter,gradesAdapter,CreditSpinnerValue,GradeSpinnerValue);
                mRecycler.setAdapter(mAdapter);
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecycler);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        Button Calculate_button = findViewById(R.id.calculate_id);
        Calculate_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(numberOfSubjects != 0)
                {
                    GradePoint = CalculateGradePoint(grades);
                    numerator=0;
                    denominator=0;
                    Log.d("SUBJECT_TAG","Number of subjects inside Calculate function:"+numberOfSubjects);
                    for(int i=0;i<numberOfSubjects;i++)
                    {
                        numerator = numerator+ credits.get(i) * GradePoint.get(i);
                        denominator = denominator + credits.get(i);
                        Log.d("LOG_TAG","numerator:"+numerator);
                        Log.d("LOG_TAG","denominator:"+denominator);
                    }
                /*Log.d("GPA_LOG","Numerator:"+numerator);
                Log.d("GPA_LOG","Denominator:"+denominator);*/
                    GPA =(double) numerator / (double)denominator;
                    DecimalFormat precision = new DecimalFormat("0.000");
                    GPA = Double.parseDouble(precision.format(GPA));
                    //Log.d("GPA_LOG","GPA:"+GPA);

                    final AlertDialog.Builder builder = new AlertDialog.Builder(GpaActivity.this);
                    builder.setTitle("Your GPA is "+GPA)
                            .setMessage("Your Total Credits "+denominator)
                            .setPositiveButton("Close",null)
                            .setNegativeButton("Save and Close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CustomDialog customDialog = new CustomDialog();
                                    customDialog.show(getSupportFragmentManager(),"CUSTOM_DIALOG");
                                    customDialog.setCancelable(false);
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                }
                else
                {
                    Toast.makeText(GpaActivity.this,"Please Select the Number Of Subjects",Toast.LENGTH_LONG).show();
                }
            }
        });

        LinearLayout Layout = findViewById(R.id.add_id);
        Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOneMore();
            }
        });

        ImageButton addButton = findViewById(R.id.add_button_id);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddOneMore();
            }
        });
    }
    private void AddOneMore()
    {
        if(numberOfSubjects < 15)
        {
            creditsAdapter.add(adapter_credits);
            gradesAdapter.add(adapter_grades);
            mAdapter.notifyDataSetChanged();
            int current_value = Integer.parseInt(spinner.getSelectedItem().toString());
            spinner.setSelection(current_value+1);
            Log.d("SUBJECT_TAG","Number of subjects After Adding:"+numberOfSubjects);
        }
        else
        {
            Toast.makeText(GpaActivity.this,"Maximum number of Subjects is reached",Toast.LENGTH_SHORT).show();
        }
    }
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(GpaActivity.this);
            builder.setTitle("Exit")
                    .setMessage("Are You Sure ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GpaActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("Cancel",null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    HashMap<Integer,Integer> CalculateGradePoint(HashMap<Integer,String> a)
    {
        @SuppressLint("UseSparseArrays") HashMap<Integer,Integer> GradePoint = new HashMap<>();
        for(int i=0;i<a.size();i++)
        {
            switch (Objects.requireNonNull(a.get(i)))
            {
                case "O":
                    GradePoint.put(i,10);
                    break;
                case "A+":
                    GradePoint.put(i,9);
                    break;
                case "A":
                    GradePoint.put(i,8);
                    break;
                case "B+":
                    GradePoint.put(i,7);
                    break;
                case "B":
                    GradePoint.put(i,6);
                    break;
                default:
                    GradePoint.put(i,0);
            }
        }
        return GradePoint;
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GpaActivity.this);
        builder.setTitle("Go back to main page")
                .setMessage("Are You Sure ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        GpaActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.d("SWIPE_TO_DELETE","Swiping calls This");
            creditsAdapter.remove(viewHolder.getAdapterPosition());
            gradesAdapter.remove(viewHolder.getAdapterPosition());

            for(int i=viewHolder.getAdapterPosition();i<numberOfSubjects-1;i++)
            {
                CreditSpinnerValue.put(i,CreditSpinnerValue.get(i+1));
                GradeSpinnerValue.put(i,GradeSpinnerValue.get(i+1));
            }
            mAdapter.notifyDataSetChanged();
            int current_value = Integer.parseInt(spinner.getSelectedItem().toString());
            spinner.setSelection(current_value-1);
            Log.d("SUBJECT_TAG","Number of subjects After swiped one entry(deleted):"+numberOfSubjects);
        }
    };

    // After giving it a name and touching save the name will come to this activity to this particular method
    // just store it in data base and display it in a recycler view(using SQLiteDatabase)....
    @Override
    public void ApplyText(String name_tag)
    {
        if(!TextUtils.isEmpty(name_tag))
        {
            boolean insertData = Students_database.AddData(name_tag,Integer.toString(denominator),Double.toString(GPA));
            if(insertData)
                Toast.makeText(GpaActivity.this,"GPA Added Successfully",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(GpaActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomDialog customDialog = new CustomDialog();
            customDialog.show(getSupportFragmentManager(),"CUSTOM_DIALOG");
            customDialog.setCancelable(false);
            Toast.makeText(GpaActivity.this,"Field Should not be empty..!",Toast.LENGTH_SHORT).show();
        }
    }
}
