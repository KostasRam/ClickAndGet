package com.superapp.mymobile.clickget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase fbdatabase;
    DatabaseReference fbref;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbdatabase = FirebaseDatabase.getInstance();
        fbref = fbdatabase.getReference("index");
    }

    public void write(View view) {
        editText = (EditText) findViewById(R.id.editText);
        fbref.setValue(editText.getText().toString());
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    public void read(View view) {
        fbref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}