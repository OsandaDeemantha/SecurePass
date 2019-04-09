package com.example.securepass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView textView;

    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = (TextView) findViewById(R.id.textTest);

        DatabaseHelper db = new DatabaseHelper(this);

        db.addCredential(new Credential("osanda","abcd","facebook.com"));
        db.addCredential(new Credential("deemantha","ygft","instagram.com"));
        db.addCredential(new Credential("nimal","sewr","ac.lk"));
        db.addCredential(new Credential("sunimal","yjhy","music.lk"));

        List<Credential> credentials = db.getAllCredentials();
        for (Credential c : credentials){
            String log = "UserName : " + c.getUsername() + ", Password : " + c.getPassword() + ", URL : " + c.getUrl()  + "\n";
            text = text + log;
        }

        textView.setText(text);

    }
}
