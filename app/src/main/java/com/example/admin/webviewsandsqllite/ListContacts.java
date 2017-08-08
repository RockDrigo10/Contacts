package com.example.admin.webviewsandsqllite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class ListContacts extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ImageButton btnAdd;
    private ArrayAdapter<String> listAdapter ;
    String[] itemName;
    String [] id;
    DataBseHelper dataBseHelper =  new DataBseHelper(this);
    private String TAG = "DatainList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        listview = (ListView) findViewById(R.id.listview);
        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        ArrayList<Contact> listContact = dataBseHelper.getContacts("-1");
        itemName = new String[listContact.size()];
        id = new String[listContact.size()];
        for (int i = 0; i < listContact.size(); i++) {
            itemName[i] = "Name: " + listContact.get(i).getName() + ";  Email: " + listContact.get(i).getEmail() + "; Phone: "
            +listContact.get(i).getNumber();
            id[i] = listContact.get(i).getId();
            Log.d(TAG, "onCreate: " +listContact.get(i).getName() + listContact.get(i).getEmail()
                    +listContact.get(i).getNumber()+listContact.get(i).getSocial()+listContact.get(i).getPhoto());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemName);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

    }
    public void newContact(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d(TAG, "onItemClick: " + id[i]);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getString(R.string.ID),id[i]);
        startActivity(intent);
    }
}
