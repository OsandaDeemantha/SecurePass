package com.example.securepass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CredentialAddActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Button btnAdd, btnView;
    private EditText title,username,password,url;
    private String ti,un,pw,ur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential_add);

        title = (EditText) findViewById(R.id.title);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        url = (EditText) findViewById(R.id.url);
        btnAdd = (Button) findViewById(R.id.add);
        btnView = (Button) findViewById(R.id.view);
        db = new DatabaseHelper(this);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CredentialAddActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti = title.getText().toString();
                un = username.getText().toString();
                pw = password.getText().toString();
                ur = url.getText().toString();
                if(ti.length() != 0 &&un.length() != 0 && pw.length() != 0 && ur.length() != 0){
                    addData(new Credential(ti,un,pw,ur));
                }else {
                    Toast.makeText(CredentialAddActivity.this, "Entry is Empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void addData(Credential credential){
        boolean insertData = db.addCredential(credential);

        if(insertData){
            Toast.makeText(CredentialAddActivity.this, "Successfully Entered",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CredentialAddActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Toast.makeText(CredentialAddActivity.this, "Something Went Wrong. Try Later",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CredentialAddActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
