package gpa.mit.india;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About");

        ImageView mWhatsApp = findViewById(R.id.whatsapp_id);
        ImageView mPhoneDialer = findViewById(R.id.phone_dialer_id);
        ImageView mMessagesApp = findViewById(R.id.messages_app_id);

        mWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + "7358882706");
                Intent WhatsApp = new Intent(Intent.ACTION_SENDTO,uri);
                WhatsApp.setPackage("com.whatsapp");
                startActivity(WhatsApp);
            }
        });

        mPhoneDialer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + Uri.encode("7358882706"));
                Intent Phone = new Intent(Intent.ACTION_DIAL,uri);
                startActivity(Phone);
            }
        });

        mMessagesApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:" + "7358882706");
                Intent MessagesApp = new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(MessagesApp);
            }
        });
    }
}
