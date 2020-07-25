package gpa.mit.india;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gpaButton = findViewById(R.id.gpa_button);
        Button cumulativeGpaButton = findViewById(R.id.Cumulative_gpa_button);
        Button viewButton = findViewById(R.id.view_data_button);
        Button viewCgpaButton = findViewById(R.id.view_my_cGpa_button);
        ImageView aboutImage = findViewById(R.id.about_id);

        gpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent GpaIntent = new Intent(MainActivity.this,GpaActivity.class);
                startActivity(GpaIntent);
            }
        });

        cumulativeGpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CumulativeIntent = new Intent(MainActivity.this,CumulativeGpaActivity.class);
                startActivity(CumulativeIntent);
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent MyGpaIntent = new Intent(MainActivity.this,MyGpaActivity.class);
                startActivity(MyGpaIntent);
            }
        });

        viewCgpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MyCGpaIntent = new Intent(MainActivity.this,ViewMyCgpaActivity.class);
                startActivity(MyCGpaIntent);
            }
        });

        aboutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent AboutIntent = new Intent(MainActivity.this,AboutPage.class);
                startActivity(AboutIntent);
            }
        });
    }
}
