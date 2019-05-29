package com.example.securepass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CredentialEditorActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Button btnUpdate,btnCancel;
    private EditText title,username,password,url;
    private String ti,un,pw,ur,notice;
    private Credential credential;
    private int id;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credential_editor);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                key= null;
            } else {
                key= extras.getString("id");
            }
        } else {
            key= (String) savedInstanceState.getSerializable("id");
        }
        id = Integer.parseInt(key);

        title = (EditText) findViewById(R.id.title);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        url = (EditText) findViewById(R.id.url);
        btnUpdate = (Button) findViewById(R.id.update);
        btnCancel = (Button) findViewById(R.id.cancel);

        db = new DatabaseHelper(this);

        credential = db.getCredential(id);

        title.setText(credential.getTitle());
        username.setText(credential.getUsername());
        password.setText(credential.getPassword());
        url.setText(credential.getUrl());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CredentialEditorActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ti = title.getText().toString();
                un = username.getText().toString();
                pw = password.getText().toString();
                ur = url.getText().toString();
                if(ti.length() != 0 && un.length() != 0 && pw.length() != 0 && ur.length() != 0){
                    if (ti.equals(credential.getTitle()) && un.equals(credential.getUsername()) && pw.equals(credential.getPassword()) && ur.equals(credential.getUrl())){
                        notice = "Nothing Changed.";
                    }else {
                        notice = "Successfully Updated.";
                    }
                    credential.setTitle(ti);
                    credential.setUsername(un);
                    credential.setPassword(pw);
                    credential.setUrl(ur);
                    updateData(credential, notice);
                }else {
                    Toast.makeText(CredentialEditorActivity.this, "Entry is empty",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void updateData(Credential credential, String notice){
        int updateData = db.updateCredential(credential);

        if(updateData == -1){
            Toast.makeText(CredentialEditorActivity.this, "Something went wrong",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CredentialEditorActivity.this, HomeActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(CredentialEditorActivity.this, notice,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CredentialEditorActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}
