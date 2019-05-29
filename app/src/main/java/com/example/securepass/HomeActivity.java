package com.example.securepass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private DatabaseHelper db;
    private Button btnView;
    private List<Credential> credentials;
    private CustomAdaptor customAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        list = (ListView) findViewById(R.id.list);

        btnView = (Button) findViewById(R.id.view);

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CredentialAddActivity.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(this);

        credentials = db.getAllCredentials();

        customAdaptor = new CustomAdaptor(this, credentials);
        list.setAdapter(customAdaptor);

        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(HomeActivity.this, "Item Clicked " ,Toast.LENGTH_LONG).show();
    }

    class CustomAdaptor extends BaseAdapter{

        private LayoutInflater inflater;
        private List<Credential> items;
        private ImageButton btnDel,btnEdit;
        private TextView title,url;

        public CustomAdaptor(Activity context, List<Credential> items){
            super();

            this.items = items;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public boolean isEnabled(int arg0)
        {
            return true;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View vi = convertView;

            if(convertView==null){
                vi = inflater.inflate(R.layout.list_layout, null);
            }

            final Credential item = items.get(position);

            title = (TextView)vi.findViewById(R.id.textView_title);
            url = (TextView)vi.findViewById(R.id.textView_url);

            btnDel = (ImageButton) vi.findViewById(R.id.btn_delete);
            btnEdit = (ImageButton) vi.findViewById(R.id.btn_edit);

            title.setText(item.getTitle());
            url.setText(item.getUrl());

            btnEdit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, CredentialEditorActivity.class);
                    //To pass whole model data with intent:
                    intent.putExtra("id", String.valueOf(item.getId()));
                    startActivity(intent);
                }
            });

            btnDel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    db.deleteCredential(item);
                                    credentials.remove(item);
                                    notifyDataSetChanged();
                                    Toast.makeText(HomeActivity.this, "Item Deleted" ,Toast.LENGTH_LONG).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    Toast.makeText(HomeActivity.this, "Canceled" ,Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setMessage("Are you sure you want to delete this item?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });
            return vi;
        }
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
