package gpa.mit.india;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ViewMyCgpaActivity extends AppCompatActivity implements CustomCgpaEditAlertDialog.CustomCgpaEditDialogListener,CustomAddCgpaAlertDialog.CustomAddCgpaAlertDialogListener
{
    DatabaseHelperCgpa Students_database;
    RecyclerView mRecycler;
    RecyclerViewCgpaAdapter mAdapter;
    public HashMap<Integer,String> NameList;
    public HashMap<Integer,String> CreditsList;
    public HashMap<Integer,String> GpaList;

    public static HashMap<Integer,String> CreditsListToSend;
    public static HashMap<Integer,String> GpaListToSend;
    int counter=0;

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_cgpa);

        Students_database = new DatabaseHelperCgpa(this);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My CGPAs");

        NameList = new HashMap<>();
        CreditsList = new HashMap<>();
        GpaList = new HashMap<>();

        if(GpaListToSend == null && CreditsListToSend == null)
        {
            CreditsListToSend = new HashMap<>();
            GpaListToSend = new HashMap<>();
        }

        Cursor data = Students_database.ViewData();
        int count=0;
        while(data.moveToNext())
        {
            NameList.put(count,data.getString(0));
            CreditsList.put(count,data.getString(1));
            GpaList.put(count,data.getString(2));
            count++;
        }

        Collection<String> NameValues = NameList.values();
        Collection<String> CreditsValues = CreditsList.values();
        Collection<String> GpaValues = GpaList.values();

        ArrayList<String> nameList = new ArrayList<>(NameValues);
        ArrayList<String> creditsList = new ArrayList<>(CreditsValues);
        ArrayList<String> gpaList = new ArrayList<>(GpaValues);

        mRecycler = findViewById(R.id.cGpa_recycler);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerViewCgpaAdapter(ViewMyCgpaActivity.this, nameList, creditsList, gpaList,counter);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.add_gpa_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_id)
        {
            CustomAddCgpaAlertDialog customAddAlertDialog = new CustomAddCgpaAlertDialog();
            customAddAlertDialog.show((ViewMyCgpaActivity.this).getSupportFragmentManager(),"CUSTOM_EDIT_DIALOG");
            customAddAlertDialog.setCancelable(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ApplyEditDataText(String name_id, String name_tag, String credits, String gpa)
    {
        if(!TextUtils.isEmpty(name_tag) && !TextUtils.isEmpty(credits) && !TextUtils.isEmpty(gpa))
        {
            boolean result = Students_database.EditData(name_id,name_tag,credits,gpa);
            if(result)
            {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(ViewMyCgpaActivity.this,"CGPA Updated Successfully",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(ViewMyCgpaActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(ViewMyCgpaActivity.this,"Fields Should not be empty..!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void ApplyAddCgpaDataText(String name_tag, String credits, String gpa)
    {
        if(!TextUtils.isEmpty(name_tag) && !TextUtils.isEmpty(credits) && !TextUtils.isEmpty(gpa))
        {
            boolean insertData = Students_database.AddData(name_tag,credits,gpa);
            if(insertData)
            {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
                Toast.makeText(ViewMyCgpaActivity.this,"GPA Added Successfully",Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(ViewMyCgpaActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomAddCgpaAlertDialog customAddAlertDialog = new CustomAddCgpaAlertDialog();
            customAddAlertDialog.show((ViewMyCgpaActivity.this).getSupportFragmentManager(),"CUSTOM_EDIT_DIALOG");
            customAddAlertDialog.setCancelable(false);
            Toast.makeText(ViewMyCgpaActivity.this,"Field Should not be empty..!",Toast.LENGTH_SHORT).show();
        }
    }
}
